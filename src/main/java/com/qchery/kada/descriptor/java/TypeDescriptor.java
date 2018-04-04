package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/4/4 22:17
 */
public class TypeDescriptor {

    private String packageName;

    private String typeName;

    private boolean primitive;

    public TypeDescriptor(String packageName, String typeName) {
        this.packageName = packageName;
        this.typeName = typeName;
    }

    public TypeDescriptor(String packageName, String typeName, boolean primitive) {
        this(packageName, typeName);
        this.primitive = primitive;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    @Override
    public String toString() {
        return "TypeDescriptor{" +
                "packageName='" + packageName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", primitive=" + primitive +
                '}';
    }
}
