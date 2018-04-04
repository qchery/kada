package com.qchery.kada.db;

import com.qchery.kada.descriptor.java.TypeDescriptor;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * sql 、 Java 数据类型匹配
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:55:53
 */
public class TypeMap {
    private static Map<Integer, TypeDescriptor> typeCache = new HashMap<>();

    public static final TypeDescriptor STRING = new TypeDescriptor("java.lang", "String");

    public static final TypeDescriptor LONG = new TypeDescriptor("java.lang", "Long");

    public static final TypeDescriptor BYTE_ARRAY = new TypeDescriptor("java.lang", "Byte[]");

    public static final TypeDescriptor BOOLEAN = new TypeDescriptor("java.lang", "Boolean");

    public static final TypeDescriptor DATE = new TypeDescriptor("java.util", "Date");

    public static final TypeDescriptor BIG_DECIMAL = new TypeDescriptor("java.math", "BigDecimal");

    public static final TypeDescriptor DOUBLE = new TypeDescriptor("java.lang", "Double");

    public static final TypeDescriptor FLOAT = new TypeDescriptor("java.lang", "Float");

    public static final TypeDescriptor INTEGER = new TypeDescriptor("java.lang", "Integer");

    public static final TypeDescriptor OBJECT = new TypeDescriptor("java.lang", "Object");

    public static final TypeDescriptor SHORT = new TypeDescriptor("java.lang", "Short");

    public static final TypeDescriptor TIME = new TypeDescriptor("java.sql", "Time");

    public static final TypeDescriptor BYTE = new TypeDescriptor("java.lang", "Byte");

    static {
        typeCache.put(Types.BIGINT, LONG);
        typeCache.put(Types.BINARY, BYTE_ARRAY);
        typeCache.put(Types.BIT, BOOLEAN);
        typeCache.put(Types.BLOB, BYTE_ARRAY);
        typeCache.put(Types.CHAR, STRING);
        typeCache.put(Types.CLOB, STRING);
        typeCache.put(Types.DATE, DATE);
        typeCache.put(Types.DECIMAL, BIG_DECIMAL);
        typeCache.put(Types.DOUBLE, DOUBLE);
        typeCache.put(Types.FLOAT, FLOAT);
        typeCache.put(Types.INTEGER, INTEGER);
        typeCache.put(Types.JAVA_OBJECT, OBJECT);
        typeCache.put(Types.LONGVARBINARY, BYTE_ARRAY);
        typeCache.put(Types.LONGVARCHAR, STRING);
        typeCache.put(Types.NUMERIC, BIG_DECIMAL);
        typeCache.put(Types.OTHER, OBJECT);
        typeCache.put(Types.REAL, FLOAT);
        typeCache.put(Types.SMALLINT, SHORT);
        typeCache.put(Types.TIME, TIME);
        typeCache.put(Types.TIMESTAMP, DATE);
        typeCache.put(Types.TINYINT, BYTE);
        typeCache.put(Types.VARBINARY, BYTE_ARRAY);
        typeCache.put(Types.VARCHAR, STRING);
    }

    public static TypeDescriptor getJavaType(int sqlType) {
        return typeCache.get(sqlType);
    }
}
