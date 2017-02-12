package com.qchery.generate.db;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * sql 、 Java 数据类型匹配
 * @author Chery
 * @date 2016年5月15日 - 下午7:55:53
 */
public class TypeMap {
    private static Map<Integer, String> typeCache = new HashMap<>();
    
    static {
        typeCache.put(Types.BIGINT, "long");
        typeCache.put(Types.BINARY, "byte[]");
        typeCache.put(Types.BIT, "Boolean");
        typeCache.put(Types.BLOB, "byte[]");
        typeCache.put(Types.CHAR, "String");
        typeCache.put(Types.CLOB, "String");
        typeCache.put(Types.DATE, "java.sql.Date");
        typeCache.put(Types.DECIMAL, "java.math.BigDecimal");
        typeCache.put(Types.DOUBLE, "double");
        typeCache.put(Types.FLOAT, "double");
        typeCache.put(Types.INTEGER, "int");
        typeCache.put(Types.JAVA_OBJECT, "java.lang.Object");
        typeCache.put(Types.LONGVARBINARY, "byte[]");
        typeCache.put(Types.LONGVARCHAR, "String");
        typeCache.put(Types.NUMERIC, "java.math.BigDecimal");
        typeCache.put(Types.OTHER, "java.lang.Object");
        typeCache.put(Types.REAL, "float");
        typeCache.put(Types.SMALLINT, "short");
        typeCache.put(Types.TIME, "java.sql.Time");
        typeCache.put(Types.TIMESTAMP, "java.sql.Date");
        typeCache.put(Types.TINYINT, "byte");
        typeCache.put(Types.VARBINARY, "byte[]");
        typeCache.put(Types.VARCHAR, "String");
    }
    
    public static String getJavaType(int sqlType) {
        return typeCache.get(sqlType);
    }
}
