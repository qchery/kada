package com.qchery.generate.convertor;

/**
 * ORM 名称转换器
 * @author chinrui1016@163.com
 * @date 2016年5月15日 - 下午9:27:20
 */
public interface NameConvertor {
    
    /**
     * 列名转属性名
     * @param columnName
     * @return
     */
    String toFieldName(String columnName);
    
    /**
     * 表名转类名
     * @param tableName
     * @return
     */
    String toClassName(String tableName);
    
    /**
     * 类名转表名
     * @param className
     * @return
     */
    String toTableName(String className);
    
    /**
     * 属性名转列名
     * @param fieldName
     * @return
     */
    String toColumnName(String fieldName);
}
