package com.qchery.kada.convertor;

/**
 * ORM 名称转换器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:20
 */
public interface NameConvertor {

    /**
     * 列名转属性名
     *
     * @param columnName 列名
     * @return 属性名
     */
    String toFieldName(String columnName);

    /**
     * 表名转类名
     *
     * @param tableName 表名
     * @return 类名
     */
    String toClassName(String tableName);

    /**
     * 类名转表名
     *
     * @param className 类名
     * @return 表名
     */
    String toTableName(String className);

    /**
     * 属性名转列名
     *
     * @param fieldName 属性名
     * @return 列名
     */
    String toColumnName(String fieldName);
}
