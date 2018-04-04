package com.qchery.kada;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ClassDescriptor {

    /**
     * 文件编码
     */
    private Charset charset;
    /**
     * 类型声明
     */
    private TypeDescriptor typeDescriptor;
    /**
     * 字段
     */
    private List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setTypeDescriptor(TypeDescriptor typeDescriptor) {
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
