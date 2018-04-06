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
    private IClassDescriptor classDescriptor;
    /**
     * 属性名
     */
    private String fieldName;

    public FieldDescriptor(TypeDescriptor typeDescriptor, String fieldName) {
        this.classDescriptor = new ClassDescriptor(typeDescriptor);
        this.fieldName = fieldName;
    }

    public FieldDescriptor(IClassDescriptor classDescriptor, String fieldName) {
        this.classDescriptor = classDescriptor;
        this.fieldName = fieldName;
    }

    /**
     * 包含全路径的类型
     */
    public String getType() {
        return classDescriptor.getPackageName() + "." + classDescriptor.getClassName();
    }

    public String getFieldName() {
        return fieldName;
    }

    /**
     * 不包含全路径的类型
     */
    public String getSimpleType() {
        return classDescriptor.getClassName();
    }

    public boolean isNormal() {
        return classDescriptor != null;
    }

    @Override
    public String toString() {
        return "FieldDescriptor{" +
                "classDescriptor=" + classDescriptor +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
