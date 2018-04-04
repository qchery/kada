package com.qchery.kada.descriptor.java;

import java.util.ArrayList;
import java.util.List;

public class ClassDescriptor {

    /**
     * 类型声明
     */
    private TypeDescriptor typeDescriptor;
    /**
     * 字段
     */
    private List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

    public ClassDescriptor(TypeDescriptor typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
    }

    public String getPackageName() {
        return typeDescriptor.getPackageName();
    }

    public String getClassName() {
        return typeDescriptor.getTypeName();
    }

    public List<FieldDescriptor> getFieldDescriptors() {
        return fieldDescriptors;
    }

    public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptors.add(fieldDescriptor);
    }

}
