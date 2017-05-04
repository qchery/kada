package com.qchery.kada;

/**
 * 生成Item的关键字
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:38
 */
public class FieldDescriptor {
    private String type;        // 类型
    private String fieldName;   // 属性名

    public FieldDescriptor(String type, String fieldName) {
        this.type = type;
        this.fieldName = fieldName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "FieldDescriptor{" +
                "type='" + type + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
