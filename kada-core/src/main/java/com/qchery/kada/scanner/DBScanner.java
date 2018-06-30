package com.qchery.kada.scanner;

import com.qchery.kada.descriptor.db.ColumnInfo;
import com.qchery.kada.descriptor.db.TableInfo;

import java.sql.Connection;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:11
 */
public interface DBScanner {

    /**
     * 扫描所有表信息
     *
     * @param conn 链接
     * @return 所有表描述
     */
    List<TableInfo> scannerTables(Connection conn);

    /**
     * 扫描数据库，获取所有子项组合
     *
     * @param conn      连接
     * @param tableName 表名
     * @return {@link List}
     */
    List<ColumnInfo> scannerColumns(Connection conn, String tableName);

}
