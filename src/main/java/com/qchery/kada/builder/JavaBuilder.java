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

    protected String getMainContent(List<MappingItem> mappingItems) {
        StringBuilder fields = new StringBuilder();
        StringBuilder methods = new StringBuilder();
        for (MappingItem mappingItem : mappingItems) {
            String javaType = mappingItem.getJavaType();
            if (javaType.contains(".")) {
                javaType = javaType.substring(javaType.lastIndexOf(".") + 1, javaType.length());
            }
            String fieldName = mappingItem.getFieldName();
            String fcuFieldName = StringUtil.upperFirstChar(fieldName);
            fields.append(delcareField(javaType, fieldName, mappingItem.getComment()));
            methods.append(delcareGetMethod(javaType, fieldName, fcuFieldName));
            methods.append(declareSetMethod(javaType, fieldName, fcuFieldName));
        }

        return fields.append(methods).toString();
    }

    /**
     * 获取字段声明
     *
     * @param javaType  数据类型
     * @param fieldName 字段名
     * @param comment   注释
     * @return 字段声明
     */
    private String delcareField(String javaType, String fieldName, String comment) {
        return String.format("/** %s */ private %s %s;\n", comment, javaType, fieldName);
    }

    private String declareSetMethod(String javaType, String fieldName,
                                    String fcuFieldName) {
        String setMethod = String.format("public void set%s(%s %s) {\n"
                + "    this.%s = %s;\n"
                + "}\n", fcuFieldName, javaType, fieldName, fieldName, fieldName);
        return setMethod;
    }

    private String delcareGetMethod(String javaType, String fieldName,
                                    String fcuFieldName) {
        String getMethod = String.format("public %s get%s() {\n"
                + "    return this.%s;\n"
                + "}\n", javaType, fcuFieldName, fieldName);
        return getMethod;
    }

    @Override
    public String getContent(Mapping mapping) {
        List<MappingItem> mappingItems = mapping.getMappingItems();
        return String.format("package %s;\n\n"
                + "%s/** %s */"
                + "public class %s {\n"
                + "%s"
                + "}", mapping.getPackageName(), getImportDeclare(mappingItems), mapping.getTableComment(), mapping.getClassName(), getMainContent(mappingItems));
    }

    private String getImportDeclare(List<MappingItem> mappingItems) {
        StringBuilder importDelcare = new StringBuilder();
        Set<String> importSet = new HashSet<>();
        for (MappingItem fieldDescriptor : mappingItems) {
            String type = fieldDescriptor.getJavaType();
            if (type.contains(".") && !importSet.contains(type)) {
                importSet.add(type);
            }
        }

        for (String importType : importSet) {
            importDelcare.append("import ");
            importDelcare.append(importType);
            importDelcare.append(";\n");
        }

        if (importDelcare.length() > 0) {
            importDelcare.append("\n");
        }

        return importDelcare.toString();
    }

    @Override
    public String getFileName(String className) {
        return className + ".java";
    }

}
