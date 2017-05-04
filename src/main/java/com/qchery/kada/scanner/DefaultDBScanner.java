package com.qchery.kada.scanner;

import com.qchery.kada.ColumnDescriptor;
import com.qchery.kada.DBOrmer;
import com.qchery.kada.TableDescriptor;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:12
 */
public class DefaultDBScanner implements DBScanner {

    private static final String COLUMN_NAME = "COLUMN_NAME";

    private Logger logger = LoggerFactory.getLogger(DBOrmer.class);

    @Override
    public TableDescriptor scannerTable(Connection conn, String tableName) {
        List<String> primaryKeys = getPrimaryKeys(conn, tableName);

        logger.debug("tableName={} | primaryKeys={}", tableName, primaryKeys);

        ResultSet columns = null;
        TableDescriptor tableDescriptor = new TableDescriptor(tableName);
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            columns = databaseMetaData.getColumns(conn.getCatalog(), getSchema(conn), tableName.toUpperCase(), "%");
            columns.getMetaData();

            while (columns.next()) {
                ColumnDescriptor columnDescriptor = new ColumnDescriptor();

                // 名称
                String columnName = columns.getString(COLUMN_NAME);
                columnDescriptor.setColumnName(columnName);

                // 描述
                String remarks = columns.getString("REMARKS");
                if (remarks == null || remarks.equals("")) {
                    remarks = columnName;
                }
                columnDescriptor.setComment(remarks);

                // 数据类型
                int dbType = columns.getInt("DATA_TYPE");
                columnDescriptor.setDbType(dbType);

                // 是否为空
                columnDescriptor.setNotNull(DatabaseMetaData.columnNoNulls == columns.getInt("NULLABLE"));
                // 长度
                columnDescriptor.setLength(columns.getInt("COLUMN_SIZE"));
                // 主键
                columnDescriptor.setPrimaryKey(isPrimaryKey(primaryKeys, columnName));

                tableDescriptor.addColumnDescriptor(columnDescriptor);
            }

        } catch (SQLException e) {
            logger.error("msg={}", "获取参数列失败", e);
        } finally {
            DbUtils.closeQuietly(columns);
        }

        return tableDescriptor;
    }

    /**
     * 获取主键属性集
     *
     * @param conn      获取表中的所有主键名称
     * @param tableName 表名
     * @return
     * @throws SQLException
     */
    private List<String> getPrimaryKeys(Connection conn, String tableName) {
        ResultSet keyRs = null;
        List<String> keyList = new ArrayList<>();
        try {
            keyRs = conn.getMetaData().getPrimaryKeys(conn.getCatalog(), "%", tableName);

            while (keyRs.next()) {
                keyList.add(keyRs.getString(COLUMN_NAME));
            }
        } catch (SQLException e) {
            logger.error("msg={}", "获取主键失败", e);
        } finally {
            DbUtils.closeQuietly(keyRs);
        }
        return keyList;
    }

    private String getSchema(Connection conn) throws SQLException {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new SQLException("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase();
    }

    /**
     * 判断字段是否为主键
     *
     * @param keys       主键属性集
     * @param columnName 当前属性
     * @return
     * @throws SQLException
     */
    private boolean isPrimaryKey(List<String> keys, String columnName) {
        boolean result = false;
        for (String key : keys) {
            if (key.equals(columnName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private static class ScannerConfig {
        private boolean userJavaType;
        private String tableName;

        public static ScannerConfig custom() {
            ScannerConfig scannerConfig = new ScannerConfig();
            scannerConfig.setUserJavaType(true);
            return scannerConfig;
        }

        public ScannerConfig setUserJavaType(boolean userJavaType) {
            this.userJavaType = userJavaType;
            return this;
        }

        public ScannerConfig setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public boolean isUserJavaType() {
            return userJavaType;
        }

        public String getTableName() {
            return tableName;
        }
    }
}
