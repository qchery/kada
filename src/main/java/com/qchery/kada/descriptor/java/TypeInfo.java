package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/4/4 22:17
 */
public class TypeInfo {

    public static final String PKG_JAVA_LANG = "java.lang";
    public static final String PKG_JAVA_UTIL = "java.util";

    public static final TypeInfo STRING = new TypeInfo(PKG_JAVA_LANG, "String", true);
    public static final TypeInfo LONG = new TypeInfo("java.lang", "Long", true);
    public static final TypeInfo BYTE_ARRAY = new TypeInfo("java.lang", "Byte[]");
    public static final TypeInfo BOOLEAN = new TypeInfo("java.lang", "Boolean", true);
    public static final TypeInfo DATE = new TypeInfo(PKG_JAVA_UTIL, "Date");
    public static final TypeInfo BIG_DECIMAL = new TypeInfo("java.math", "BigDecimal");
    public static final TypeInfo DOUBLE = new TypeInfo("java.lang", "Double", true);
    public static final TypeInfo FLOAT = new TypeInfo("java.lang", "Float", true);
    public static final TypeInfo INTEGER = new TypeInfo("java.lang", "Integer", true);
    public static final TypeInfo OBJECT = new TypeInfo("java.lang", "Object");
    public static final TypeInfo SHORT = new TypeInfo("java.lang", "Short", true);
    public static final TypeInfo TIME = new TypeInfo("java.sql", "Time");
    public static final TypeInfo BYTE = new TypeInfo("java.lang", "Byte", true);

    private String packageName;

    private String typeName;

    private boolean primitive;

    public TypeInfo(String packageName, String typeName) {
        this.packageName = packageName;
        this.typeName = typeName;
    }

    public TypeInfo(String packageName, String typeName, boolean primitive) {
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
        return "TypeInfo{" +
                "packageName='" + packageName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", primitive=" + primitive +
                '}';
    }
}
