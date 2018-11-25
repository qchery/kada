package com.qchery.kada.builder.java;

import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.Space;
import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.IClassInfo;
import com.qchery.kada.utils.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.qchery.kada.descriptor.Space.ofFour;

/**
 * @author Chery
 * @date 2018/4/14 16:42
 */
public abstract class OriginalJavaContentBuilder implements JavaContentBuilder, ContentBuilder {

    private AnnotationStrategy annotationStrategy;

    @Override
    public String build(Charset charset, Mapping mapping) {
        return build(mapping.getClassInfo());
    }

    @Override
    public String build(IClassInfo classInfo) {
        String builder = "package " + classInfo.toEntityPackage() + ";\n\n" +
                declareImports(classInfo) + "\n" +
                CommentBuilder.build(classInfo) +
                declareClassAnnotations() +
                "public class " + classInfo.getClassName() + " implements Serializable {\n" +
                getMainContent(classInfo) + "\n}";
        return builder;
    }

    protected abstract String getMainContent(IClassInfo classInfo);

    protected abstract String declareClassAnnotations();

    protected StringBuilder declareFields(IClassInfo classInfo) {
        StringBuilder fields = new StringBuilder();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            if (StringUtils.isNotBlank(fieldInfo.getComment())) {
                fields.append(ofFour()).append("/**\n")
                        .append(Space.ofFive()).append("* ").append(fieldInfo.getComment()).append("\n")
                        .append(Space.ofFive()).append("*/\n");
            }
            if (annotationStrategy != null) {
                fields.append(ofFour()).append(annotationStrategy.declareAnnotation(fieldInfo.getAnnotationName()));
            }
            fields.append(ofFour()).append("private ").append(fieldInfo.getSimpleType())
                    .append(" ").append(fieldInfo.getFieldName()).append(";\n");
        }
        return fields;
    }

    private String declareImports(IClassInfo classInfo) {

        StringBuilder imports = new StringBuilder();
        Collection<String> importTypes = classInfo.getImportTypes();
        importTypes.add("java.io.Serializable");
        importTypes.addAll(getExtraImportTypes());
        for (String importType : importTypes) {
            imports.append("import ").append(importType).append(";\n");
        }

        return imports.toString();
    }

    protected List<String> getExtraImportTypes() {
        List<String> extraImportTypes = new ArrayList<>();
        if (annotationStrategy != null) {
            extraImportTypes.add(annotationStrategy.dependClass());
        }
        return extraImportTypes;
    }

    public void setAnnotationStrategy(AnnotationStrategy annotationStrategy) {
        this.annotationStrategy = annotationStrategy;
    }
}
