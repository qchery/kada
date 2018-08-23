package com.qchery.kada.builder.java;

import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.Space;
import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.IClassInfo;
import com.qchery.kada.utils.StringUtils;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import static com.qchery.kada.descriptor.Space.*;

/**
 * @author Chery
 * @date 2018/4/14 16:42
 */
public class OriginalJavaContentBuilder implements JavaContentBuilder, ContentBuilder {

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
                "public class " + classInfo.getClassName() + " implements Serializable {\n" +
                getMainContent(classInfo) + "\n}";
        return builder;
    }

    private String getMainContent(IClassInfo classInfo) {
        StringBuilder fields = declareFields(classInfo);
        StringBuilder setGetMethods = declareSetGetMethods(classInfo);
        StringBuilder toStringMethod = declareToString(classInfo);
        return fields.append("\n").append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(IClassInfo classInfo) {
        StringBuilder methods = new StringBuilder();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            methods.append(formatGetMethod(fieldInfo));
            methods.append(formatSetMethod(fieldInfo));
        }
        return methods;
    }

    private StringBuilder declareFields(IClassInfo classInfo) {
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

    private StringBuilder declareToString(IClassInfo classInfo) {
        List<FieldInfo> fieldInfos = classInfo.getFieldInfos();
        StringBuilder toStringMethod = new StringBuilder();
        toStringMethod.append(ofFour()).append("@Override\n")
                .append(ofFour()).append("public String toString() {\n")
                .append(ofEight()).append("return \"").append(classInfo.getClassName()).append(" [\"\n");
        for (int i = 0; i < fieldInfos.size(); i++) {
            FieldInfo fieldInfo = fieldInfos.get(i);
            toStringMethod.append(ofSixteen()).append("+ ").append("\"")
                    .append(fieldInfo.getFieldName()).append(" = \" + ")
                    .append(fieldInfo.getFieldName()).append(" + \"");
            if (i == fieldInfos.size() - 1) {
                toStringMethod.append("]\";\n");
            } else {
                toStringMethod.append(",\"\n");
            }
        }
        toStringMethod.append(ofFour()).append("}");
        return toStringMethod;
    }

    private String formatSetMethod(FieldInfo fieldInfo) {
        String fieldName = fieldInfo.getFieldName();
        String setMethod = String.format(ofFour() + "public void set%s(%s %s) {\n"
                        + ofEight() + "this.%s = %s;\n"
                        + ofFour() + "}\n\n", fieldInfo.getFcuFieldName(),
                fieldInfo.getSimpleType(), fieldName, fieldName, fieldName);
        return setMethod;
    }

    private String formatGetMethod(FieldInfo fieldInfo) {
        String getMethod = String.format(ofFour() + "public %s get%s() {\n"
                        + ofEight() + "return this.%s;\n"
                        + ofFour() + "}\n\n", fieldInfo.getSimpleType(),
                fieldInfo.getFcuFieldName(), fieldInfo.getFieldName());
        return getMethod;
    }

    private String declareImports(IClassInfo classInfo) {

        StringBuilder imports = new StringBuilder();
        Collection<String> importTypes = classInfo.getImportTypes();
        importTypes.add("java.io.Serializable");
        if (annotationStrategy != null) {
            importTypes.add(annotationStrategy.dependClass());
        }
        for (String importType : importTypes) {
            imports.append("import ").append(importType).append(";\n");
        }

        return imports.toString();
    }

    public void setAnnotationStrategy(AnnotationStrategy annotationStrategy) {
        this.annotationStrategy = annotationStrategy;
    }
}
