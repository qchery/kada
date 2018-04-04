package com.qchery.kada.descriptor.java;

/**
 * 生成Item的关键字
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:38
 */
public class FieldDescriptor {

    /**
     * 类型描述
     */
    private TypeDescriptor typeDescriptor;
    /**
     * 属性名
     */
    private String fieldName;

    public FieldDescriptor(TypeDescriptor typeDescriptor, String fieldName) {
        this.typeDescriptor = typeDescriptor;
        this.fieldName = fieldName;
    }

    /**
     * 包含全路径的类型
     */
    public String getType() {
        return typeDescriptor.getPackageName() + "." + typeDescriptor.getTypeName();
    }

    public String getFieldName() {
        return fieldName;
    }

    /**
     * 不包含全路径的类型
     */
    public String getSimpleType() {
        return typeDescriptor.getTypeName();
    }

    @Override
    public String toString() {
        return "FieldDescriptor{" +
                "typeDescriptor=" + typeDescriptor +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
