package com.qchery.generate.builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qchery.common.utils.StringUtil;
import com.qchery.generate.Item;
import com.qchery.generate.ObjectDescriptor;

/**
 * 映射生成Java类
 * @author chinrui1016@163.com
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaBuilder implements FileBuilder {
    
    protected String getMainContent(List<Item> items) {
        StringBuffer fields = new StringBuffer();
        StringBuffer methods = new StringBuffer();
        for (Item item : items) {
            String javaType = item.getType();
            if (javaType.contains(".")) {
                javaType = javaType.substring(javaType.lastIndexOf(".") + 1, javaType.length());
            }
            String fieldName = item.getFieldName();
            String fcuFieldName = StringUtil.upperCaseFirst(fieldName);
            fields.append(delcareField(javaType, fieldName));
            methods.append(delcareGetMethod(javaType, fieldName, fcuFieldName));
            methods.append(declareSetMethod(javaType, fieldName, fcuFieldName));
        }
        
        return fields.append(methods).toString();
    }
    
    /**
     * 获取字段声明
     * @param javaType      数据类型
     * @param fieldName     字段名
     * @return
     */
    private String delcareField(String javaType, String fieldName) {
        return String.format("private %s %s;\n", javaType, fieldName);
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
    public String getContent(ObjectDescriptor descriptor) {
        List<Item> listItems = descriptor.getItems();
        return String.format("package %s;\n"
                + "%s"
                + "public class %s {\n"
                + "%s"
                + "}", descriptor.getPackageName(), getImportDeclare(listItems), descriptor.getClassName(), getMainContent(listItems));
    }

    private String getImportDeclare(List<Item> listItems) {
        StringBuffer importDelcare = new StringBuffer();
        Set<String> importSet = new HashSet<>();
        for (Item item : listItems) {
            String type = item.getType();
            if (type.contains(".") && !importSet.contains(type)) {
                importSet.add(type);
            }
        }
        
        for (String importType : importSet) {
            importDelcare.append("import ");
            importDelcare.append(importType);
            importDelcare.append(";\n");
        }
        
        return importDelcare.toString();
    }

    @Override
    public String getFileName(String className) {
        return className + ".java";
    }
    
}
