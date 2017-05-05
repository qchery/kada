package com.qchery.kada;

/**
 * 生成Item的关键字
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:38
 */
public class FieldDescriptor {
    /**
     * 包含全路径的类型
     */
    private String type;
    /**
     * 不包含全路径的类型
     */
    private String simpleType;
    /**
     * 属性名
     */
    private String fieldName;

    public FieldDescriptor(String type, String fieldName) {
        this.type = type;
        this.fieldName = fieldName;
        if (type.contains(".")) {
            simpleType = type.substring(type.lastIndexOf(".") + 1, type.length());
        } else {
            simpleType = type;
        }
    }

    public String getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getSimpleType() {
        return simpleType;
    }

    @Override
    public String toString() {
        return "FieldDescriptor{" +
                "type='" + type + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
