package com.qchery.kada;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ClassDescriptor {

    // 文件编码
    private Charset charset;
    // 包名
    private String packageName;
    // 类名
    private String className;
    // 字段
    private List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldDescriptor> getFieldDescriptors() {
        return fieldDescriptors;
    }

    public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptors.add(fieldDescriptor);
    }

}
