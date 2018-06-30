package com.qchery.kada.builder.java;

import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.IClassInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author Chery
 * @date 2018/4/14 16:42
 */
public class OriginalJavaContentBuilder implements JavaContentBuilder {

    private AnnotationStrategy annotationStrategy;

    @Override
    public String build(IClassInfo classInfo) {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(classInfo.getPackageName()).append(";\n\n")
                .append(declareImports(classInfo));

        if (StringUtils.isNotBlank(classInfo.getComment())) {
            builder.append("/** ").append(classInfo.getComment()).append(" */");
        }

        builder.append("public class ").append(classInfo.getClassName()).append(" implements Serializable {\n")
                .append(getMainContent(classInfo)).append("}");
        return builder.toString();
    }

    private String getMainContent(IClassInfo classInfo) {
        StringBuilder fields = declareFileds(classInfo);
        StringBuilder setGetMethods = declareSetGetMethods(classInfo);
        StringBuilder toStringMethod = declareToString(classInfo);
        return fields.append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(IClassInfo classInfo) {
        StringBuilder methods = new StringBuilder();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            methods.append(formatGetMethod(fieldInfo));
            methods.append(formatSetMethod(fieldInfo));
        }
        return methods;
    }

    private StringBuilder declareFileds(IClassInfo classInfo) {
        StringBuilder fields = new StringBuilder();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            if (StringUtils.isNotBlank(fieldInfo.getComment())) {
                fields.append("/** ").append(fieldInfo.getComment()).append(" */ ");
            }
            if (annotationStrategy != null) {
                fields.append(annotationStrategy.declareAnnotation(fieldInfo.getAnnotationName()));
            }
            fields.append("private ").append(fieldInfo.getSimpleType())
                    .append(" ").append(fieldInfo.getFieldName()).append(";\n");
        }
        return fields;
    }

    private StringBuilder declareToString(IClassInfo classInfo) {
        List<FieldInfo> fieldInfos = classInfo.getFieldInfos();
        StringBuilder toStringMethod = new StringBuilder("@Override\npublic String toString() {\n" +
                "return \"").append(classInfo.getClassName()).append(" [\"");
        for (int i = 0; i < fieldInfos.size(); i++) {
            FieldInfo fieldInfo = fieldInfos.get(i);
            toStringMethod.append("+").append("\"")
                    .append(fieldInfo.getFieldName()).append(" = \"+")
                    .append(fieldInfo.getFieldName()).append("+ \"");
            if (i == fieldInfos.size() - 1) {
                toStringMethod.append("]\";");
            } else {
                toStringMethod.append(",\"\n");
            }
        }
        toStringMethod.append("}");
        return toStringMethod;
    }

    private String formatSetMethod(FieldInfo fieldInfo) {
        String fieldName = fieldInfo.getFieldName();
        String setMethod = String.format("public void set%s(%s %s) {\n"
                        + "    this.%s = %s;\n"
                        + "}\n", fieldInfo.getFcuFieldName(),
                fieldInfo.getSimpleType(), fieldName, fieldName, fieldName);
        return setMethod;
    }

    private String formatGetMethod(FieldInfo fieldInfo) {
        String getMethod = String.format("public %s get%s() {\n"
                        + "    return this.%s;\n"
                        + "}\n", fieldInfo.getSimpleType(),
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
