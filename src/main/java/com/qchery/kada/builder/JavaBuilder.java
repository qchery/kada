package com.qchery.kada.builder;

import com.qchery.kada.Mapping;
import com.qchery.kada.MappingItem;
import com.qchery.kada.utils.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 映射生成Java类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaBuilder implements FileBuilder {

    @Override
    public String getContent(Mapping mapping) {
        List<MappingItem> mappingItems = mapping.getMappingItems();
        return String.format("package %s;\n\n"
                + "%s/** %s */"
                + "public class %s implements Serializable {\n"
                + "%s"
                + "}", mapping.getPackageName(), declareImports(mappingItems), mapping.getTableComment(), mapping.getClassName(), getMainContent(mapping));
    }

    protected String getMainContent(Mapping mapping) {
        List<MappingItem> mappingItems = mapping.getMappingItems();
        StringBuilder fields = declareFileds(mappingItems);
        StringBuilder setGetMethods = declareSetGetMethods(mappingItems);
        StringBuilder toStringMethod = declareToString(mapping, mappingItems);
        return fields.append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(List<MappingItem> mappingItems) {
        StringBuilder methods = new StringBuilder();
        for (MappingItem mappingItem : mappingItems) {
            String javaType = mappingItem.getSimpleJavaType();
            String fieldName = mappingItem.getFieldName();
            String fcuFieldName = StringUtil.upperFirstChar(fieldName);
            methods.append(formatGetMethod(javaType, fieldName, fcuFieldName));
            methods.append(formatSetMethod(javaType, fieldName, fcuFieldName));
        }
        return methods;
    }

    private StringBuilder declareFileds(List<MappingItem> mappingItems) {
        StringBuilder fields = new StringBuilder();
        for (MappingItem mappingItem : mappingItems) {
            String fieldName = mappingItem.getFieldName();
            fields.append("/** ").append(mappingItem.getComment())
                    .append(" */ private ").append(mappingItem.getSimpleJavaType())
                    .append(" ").append(fieldName).append(";\n");
        }
        return fields;
    }

    private StringBuilder declareToString(Mapping mapping, List<MappingItem> mappingItems) {
        StringBuilder toStringMethod = new StringBuilder("@Override\npublic String toString() {\n" +
                "return \"").append(mapping.getClassName()).append(" [\"");
        for (int i = 0; i < mappingItems.size(); i++) {
            MappingItem mappingItem = mappingItems.get(i);
            toStringMethod.append("+").append("\"")
                    .append(mappingItem.getFieldName()).append(" = \"+")
                    .append(mappingItem.getFieldName()).append("+ \"");
            if (i == mappingItems.size() - 1) {
                toStringMethod.append("]\";");
            } else {
                toStringMethod.append(",\"\n");
            }
        }
        toStringMethod.append("}");
        return toStringMethod;
    }

    private String formatSetMethod(String javaType, String fieldName,
                                   String fcuFieldName) {
        String setMethod = String.format("public void set%s(%s %s) {\n"
                + "    this.%s = %s;\n"
                + "}\n", fcuFieldName, javaType, fieldName, fieldName, fieldName);
        return setMethod;
    }

    private String formatGetMethod(String javaType, String fieldName,
                                   String fcuFieldName) {
        String getMethod = String.format("public %s get%s() {\n"
                + "    return this.%s;\n"
                + "}\n", javaType, fcuFieldName, fieldName);
        return getMethod;
    }

    private StringBuilder declareImports(List<MappingItem> mappingItems) {
        StringBuilder imports = new StringBuilder();
        Set<String> importSet = new HashSet<>();
        for (MappingItem mappingItem : mappingItems) {
            String type = mappingItem.getJavaType();

            if (!mappingItem.getJavaType().equals(mappingItem.getSimpleJavaType())) {
                importSet.add(type);
            }
        }

        importSet.add("java.io.Serializable");
        for (String importType : importSet) {
            imports.append("import ").append(importType).append(";\n");
        }

        return imports;
    }

    @Override
    public String getFileName(String className) {
        return className + ".java";
    }

}
