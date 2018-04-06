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

    static {
        typeCache.put(Types.BIGINT, TypeDescriptor.LONG);
        typeCache.put(Types.BINARY, TypeDescriptor.BYTE_ARRAY);
        typeCache.put(Types.BIT, TypeDescriptor.BOOLEAN);
        typeCache.put(Types.BLOB, TypeDescriptor.BYTE_ARRAY);
        typeCache.put(Types.CHAR, TypeDescriptor.STRING);
        typeCache.put(Types.CLOB, TypeDescriptor.STRING);
        typeCache.put(Types.DATE, TypeDescriptor.DATE);
        typeCache.put(Types.DECIMAL, TypeDescriptor.BIG_DECIMAL);
        typeCache.put(Types.DOUBLE, TypeDescriptor.DOUBLE);
        typeCache.put(Types.FLOAT, TypeDescriptor.FLOAT);
        typeCache.put(Types.INTEGER, TypeDescriptor.INTEGER);
        typeCache.put(Types.JAVA_OBJECT, TypeDescriptor.OBJECT);
        typeCache.put(Types.LONGVARBINARY, TypeDescriptor.BYTE_ARRAY);
        typeCache.put(Types.LONGVARCHAR, TypeDescriptor.STRING);
        typeCache.put(Types.NUMERIC, TypeDescriptor.BIG_DECIMAL);
        typeCache.put(Types.OTHER, TypeDescriptor.OBJECT);
        typeCache.put(Types.REAL, TypeDescriptor.FLOAT);
        typeCache.put(Types.SMALLINT, TypeDescriptor.SHORT);
        typeCache.put(Types.TIME, TypeDescriptor.TIME);
        typeCache.put(Types.TIMESTAMP, TypeDescriptor.DATE);
        typeCache.put(Types.TINYINT, TypeDescriptor.BYTE);
        typeCache.put(Types.VARBINARY, TypeDescriptor.BYTE_ARRAY);
        typeCache.put(Types.VARCHAR, TypeDescriptor.STRING);
    }

    public static TypeDescriptor getJavaType(int sqlType) {
        return typeCache.get(sqlType);
    }
}
