package com.qchery.kada.filter;

/**
 * 表命名过滤器
 *
 * @author qinrui@yonyou.com
 * @version 1.0.0
 * @date 2017/5/3
 */
public interface TableNameFilter {

    boolean accept(String tableName);

}
