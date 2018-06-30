package com.qchery.kada.db;

import com.qchery.kada.descriptor.java.TypeInfo;

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
    private static Map<Integer, TypeInfo> typeCache = new HashMap<>();

    static {
        typeCache.put(Types.BIGINT, TypeInfo.LONG);
        typeCache.put(Types.BINARY, TypeInfo.BYTE_ARRAY);
        typeCache.put(Types.BIT, TypeInfo.BOOLEAN);
        typeCache.put(Types.BLOB, TypeInfo.BYTE_ARRAY);
        typeCache.put(Types.CHAR, TypeInfo.STRING);
        typeCache.put(Types.CLOB, TypeInfo.STRING);
        typeCache.put(Types.DATE, TypeInfo.DATE);
        typeCache.put(Types.DECIMAL, TypeInfo.BIG_DECIMAL);
        typeCache.put(Types.DOUBLE, TypeInfo.DOUBLE);
        typeCache.put(Types.FLOAT, TypeInfo.FLOAT);
        typeCache.put(Types.INTEGER, TypeInfo.INTEGER);
        typeCache.put(Types.JAVA_OBJECT, TypeInfo.OBJECT);
        typeCache.put(Types.LONGVARBINARY, TypeInfo.BYTE_ARRAY);
        typeCache.put(Types.LONGVARCHAR, TypeInfo.STRING);
        typeCache.put(Types.NUMERIC, TypeInfo.BIG_DECIMAL);
        typeCache.put(Types.OTHER, TypeInfo.OBJECT);
        typeCache.put(Types.REAL, TypeInfo.FLOAT);
        typeCache.put(Types.SMALLINT, TypeInfo.SHORT);
        typeCache.put(Types.TIME, TypeInfo.TIME);
        typeCache.put(Types.TIMESTAMP, TypeInfo.DATE);
        typeCache.put(Types.TINYINT, TypeInfo.BYTE);
        typeCache.put(Types.VARBINARY, TypeInfo.BYTE_ARRAY);
        typeCache.put(Types.VARCHAR, TypeInfo.STRING);
    }

    public static TypeInfo getJavaType(int sqlType) {
        return typeCache.get(sqlType);
    }
}
