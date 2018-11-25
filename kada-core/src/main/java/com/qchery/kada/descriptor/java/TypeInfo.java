package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/4/4 22:17
 */
public class TypeInfo {

    public static final String PKG_JAVA_LANG = "java.lang";
    public static final String PKG_JAVA_UTIL = "java.util";

    public static final TypeInfo STRING = new TypeInfo(PKG_JAVA_LANG, "String", Category.PRIMITIVE);
    public static final TypeInfo LONG = new TypeInfo(PKG_JAVA_LANG, "Long", Category.PRIMITIVE);
    public static final TypeInfo BYTE_ARRAY = new TypeInfo(PKG_JAVA_LANG, "Byte", Category.ARRAY);
    public static final TypeInfo BOOLEAN = new TypeInfo(PKG_JAVA_LANG, "Boolean", Category.PRIMITIVE);
    public static final TypeInfo DATE = new TypeInfo(PKG_JAVA_UTIL, "Date");
    public static final TypeInfo BIG_DECIMAL = new TypeInfo("java.math", "BigDecimal");
    public static final TypeInfo DOUBLE = new TypeInfo(PKG_JAVA_LANG, "Double", Category.PRIMITIVE);
    public static final TypeInfo FLOAT = new TypeInfo(PKG_JAVA_LANG, "Float", Category.PRIMITIVE);
    public static final TypeInfo INTEGER = new TypeInfo(PKG_JAVA_LANG, "Integer", Category.PRIMITIVE);
    public static final TypeInfo OBJECT = new TypeInfo(PKG_JAVA_LANG, "Object");
    public static final TypeInfo SHORT = new TypeInfo(PKG_JAVA_LANG, "Short", Category.PRIMITIVE);
    public static final TypeInfo TIME = new TypeInfo("java.sql", "Time");
    public static final TypeInfo BYTE = new TypeInfo(PKG_JAVA_LANG, "Byte", Category.PRIMITIVE);

    private String packageName;

    private String typeName;

    private Category category;

    public enum Category {
        PRIMITIVE, ARRAY
    }

    public TypeInfo(String packageName, String typeName) {
        this.packageName = packageName;
        this.typeName = typeName;
    }

    public TypeInfo(String packageName, String typeName, Category category) {
        this(packageName, typeName);
        this.category = category;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isPrimitive() {
        return category == Category.PRIMITIVE;
    }

    public boolean isArray() {
        return category == Category.ARRAY;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "TypeInfo{" +
                "packageName='" + packageName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", category=" + category +
                '}';
    }
}
