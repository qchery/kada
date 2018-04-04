package com.qchery.kada.scanner;

import com.qchery.kada.descriptor.db.ColumnDescriptor;
import com.qchery.kada.DBOrmer;
import com.qchery.kada.descriptor.db.TableDescriptor;
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
    public List<TableDescriptor> scannerTables(Connection conn) {
        List<TableDescriptor> tableDescriptors = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            resultSet = databaseMetaData.getTables(conn.getCatalog(), getSchema(conn), "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                TableDescriptor tableDescriptor = new TableDescriptor(resultSet.getString("TABLE_NAME"));
                tableDescriptor.setComment(resultSet.getString("REMARKS"));
                tableDescriptors.add(tableDescriptor);
            }
        } catch (SQLException e) {
            logger.error("msg={}", " 扫描表元数据失败", e);
        } finally {
            DbUtils.closeQuietly(resultSet);
        }
        return tableDescriptors;
    }

    @Override
    public List<ColumnDescriptor> scannerColumns(Connection conn, String tableName) {

        ResultSet columns = null;
        List<ColumnDescriptor> columnDescriptors = new ArrayList<>();
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData();

            List<String> primaryKeys = getPrimaryKeys(conn, tableName);
            logger.debug("tableName={} | primaryKeys={}", tableName, primaryKeys);

            columns = databaseMetaData.getColumns(conn.getCatalog(), getSchema(conn), tableName, "%");

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

                columnDescriptors.add(columnDescriptor);
            }

        } catch (SQLException e) {
            logger.error("msg={}", "获取参数列失败", e);
        } finally {
            DbUtils.closeQuietly(columns);
        }

        return columnDescriptors;
    }

    /**
     * 获取主键属性集
     *
     * @param conn      获取表中的所有主键名称
     * @param tableName 表名
     * @return 所有主键
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
     * @return 是否为主键
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

}
