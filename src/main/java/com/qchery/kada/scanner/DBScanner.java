package com.qchery.kada.scanner;

import com.qchery.kada.TableDescriptor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:11
 */
public interface DBScanner {

    /**
     * 扫描数据库，获取所有子项组合
     *
     * @param conn      连接
     * @param tableName 表名
     * @return {@link List}
     * @throws SQLException
     */
    TableDescriptor scannerTable(Connection conn, String tableName);

}
