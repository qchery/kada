package com.qchery.kada.builder.java;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.IClassInfo;

import java.util.List;

/**
 * 映射生成Java类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaBuilder implements FileBuilder {

    @Override
    public String getContent(Mapping mapping) {
        IClassInfo classInfo = mapping.getClassInfo();
        return String.format("package %s;\n\n"
                        + "%s/** %s */"
                        + "public class %s implements Serializable {\n"
                        + "%s"
                        + "}", classInfo.getPackageName(), declareImports(classInfo),
                classInfo.getComment(), classInfo.getClassName(), getMainContent(classInfo));
    }

    protected String getMainContent(IClassInfo classInfo) {
        List<FieldInfo> fieldInfos = classInfo.getFieldInfos();
        StringBuilder fields = declareFileds(fieldInfos);
        StringBuilder setGetMethods = declareSetGetMethods(fieldInfos);
        StringBuilder toStringMethod = declareToString(classInfo, fieldInfos);
        return fields.append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(List<FieldInfo> fieldInfos) {
        StringBuilder methods = new StringBuilder();
        for (FieldInfo fieldInfo : fieldInfos) {
            methods.append(formatGetMethod(fieldInfo));
            methods.append(formatSetMethod(fieldInfo));
        }
        return methods;
    }

    private StringBuilder declareFileds(List<FieldInfo> fieldInfos) {
        StringBuilder fields = new StringBuilder();
        for (FieldInfo fieldInfo : fieldInfos) {
            String fieldName = fieldInfo.getFieldName();
            fields.append("/** ").append(fieldInfo.getComment())
                    .append(" */ private ").append(fieldInfo.getSimpleType())
                    .append(" ").append(fieldName).append(";\n");
        }
        return fields;
    }

    private StringBuilder declareToString(IClassInfo classInfo, List<FieldInfo> fieldInfos) {
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

    private StringBuilder declareImports(IClassInfo classInfo) {
        StringBuilder imports = new StringBuilder();
        for (String importType : classInfo.getImportTypes()) {
            imports.append("import ").append(importType).append(";\n");
        }
        return imports;
    }

    @Override
    public String getFileName(String className) {
        return className + ".java";
    }

}
