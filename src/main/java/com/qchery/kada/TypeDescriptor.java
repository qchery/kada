package com.qchery.kada;

/**
 * @author Chery
 * @date 2018/4/4 22:17
 */
public class TypeDescriptor {

    private String packageName;

    private String typeName;

    public TypeDescriptor(String packageName, String typeName) {
        this.packageName = packageName;
        this.typeName = typeName;
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

    @Override
    public String toString() {
        return "TypeDescriptor{" +
                "packageName='" + packageName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
