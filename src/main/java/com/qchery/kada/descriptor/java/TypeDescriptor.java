package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/4/4 22:17
 */
public class TypeDescriptor {

    public static final String PKG_JAVA_LANG = "java.lang";
    public static final String PKG_JAVA_UTIL = "java.util";

    public static final TypeDescriptor STRING = new TypeDescriptor(PKG_JAVA_LANG, "String", true);
    public static final TypeDescriptor LONG = new TypeDescriptor("java.lang", "Long", true);
    public static final TypeDescriptor BYTE_ARRAY = new TypeDescriptor("java.lang", "Byte[]");
    public static final TypeDescriptor BOOLEAN = new TypeDescriptor("java.lang", "Boolean", true);
    public static final TypeDescriptor DATE = new TypeDescriptor(PKG_JAVA_UTIL, "Date");
    public static final TypeDescriptor BIG_DECIMAL = new TypeDescriptor("java.math", "BigDecimal");
    public static final TypeDescriptor DOUBLE = new TypeDescriptor("java.lang", "Double", true);
    public static final TypeDescriptor FLOAT = new TypeDescriptor("java.lang", "Float", true);
    public static final TypeDescriptor INTEGER = new TypeDescriptor("java.lang", "Integer", true);
    public static final TypeDescriptor OBJECT = new TypeDescriptor("java.lang", "Object");
    public static final TypeDescriptor SHORT = new TypeDescriptor("java.lang", "Short", true);
    public static final TypeDescriptor TIME = new TypeDescriptor("java.sql", "Time");
    public static final TypeDescriptor BYTE = new TypeDescriptor("java.lang", "Byte", true);

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

    public String getTypeName() {
        return typeName;
    }

    public boolean isPrimitive() {
        return primitive;
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
