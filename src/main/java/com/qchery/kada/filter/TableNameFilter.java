package com.qchery.kada.filter;

/**
 * 表命名过滤器
 *
 * @author Chery
 * @date 2017/5/3
 */
public interface TableNameFilter {

    boolean accept(String tableName);

}
