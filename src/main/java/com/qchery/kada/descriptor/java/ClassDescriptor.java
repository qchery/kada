package com.qchery.kada.descriptor.java;

import java.util.ArrayList;
import java.util.List;

public class ClassDescriptor implements IClassDescriptor {

    /**
     * 类型声明
     */
    private TypeDescriptor typeDescriptor;
    /**
     * 字段
     */
    private List<FieldDescriptor> fieldDescriptors;

    public static ClassDescriptor of(String packageName, String typeName) {
        return of(new TypeDescriptor(packageName, typeName));
    }

    public static ClassDescriptor of(TypeDescriptor typeDescriptor) {
        return new ClassDescriptor(typeDescriptor);
    }

    public static ClassDescriptor of(Class<?> clazz) {
        String packageName = clazz.getPackage().getName();
        String simpleName = clazz.getSimpleName();
        return of(new TypeDescriptor(packageName, simpleName));
    }

    protected ClassDescriptor(TypeDescriptor typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
        // 当类型不为基础类型时，分配字段列表
        if (!typeDescriptor.isPrimitive()) {
            fieldDescriptors = new ArrayList<>();
        }
    }

    @Override
    public String getPackageName() {
        return typeDescriptor.getPackageName();
    }

    @Override
    public String getClassName() {
        return typeDescriptor.getTypeName();
    }

    @Override
    public List<FieldDescriptor> getFieldDescriptors() {
        if (typeDescriptor.isPrimitive()) {
            throw new RuntimeException("基础类型无字段描述");
        }
        return fieldDescriptors;
    }

    @Override
    public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
        if (typeDescriptor.isPrimitive()) {
            throw new RuntimeException("基础类型无字段描述");
        }
        this.fieldDescriptors.add(fieldDescriptor);
    }

    @Override
    public boolean isPrimitive() {
        return typeDescriptor.isPrimitive();
    }

}
