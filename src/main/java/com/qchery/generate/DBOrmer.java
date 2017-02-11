package com.qchery.generate;

import com.qchery.generate.builder.FileBuilder;
import com.qchery.generate.convertor.DefaultNameConvertor;
import com.qchery.generate.convertor.NameConvertor;
import com.qchery.generate.db.DBHelper;
import com.qchery.generate.db.TypeMap;
import com.qchery.generate.exception.ConfigException;
import org.apache.commons.dbutils.DbUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将数据表属性列转换在Java对象属性的工具类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:55:37
 */
public class DBOrmer {

    private static final String COLUMN_NAME = "COLUMN_NAME";

    private DBHelper dbHelper;    // 支持多数据库
    private NameConvertor nameConvertor;
    private FileBuilder fileBuilder;
    private Charset fileCharset;
    private boolean userJavaType;
    private String packageName;

    private DBOrmer() {
    }

    /**
     * 将数据库字段转换成对应的Java文件
     */
    public void generateFile() {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = dbHelper.getConnection();
            rs = conn.prepareStatement(dbHelper.getTableNames()).executeQuery();

            while (rs.next()) {
                generateFile(conn, rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, null, rs);
        }
    }

    /**
     * 将数据库表字段转换成对就的JAVA代码
     *
     * @param tableName
     */
    public void generateFile(String tableName) {
        Connection conn = null;
        try {
            conn = dbHelper.getConnection();
            generateFile(conn, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private void generateFile(Connection conn, String tableName) {
        try {
            ObjectDescriptor descriptor = new ObjectDescriptor();
            descriptor.setPackageName(packageName);
            descriptor.setClassName(nameConvertor.toClassName(tableName));
            descriptor.setTableName(tableName);
            descriptor.setItems(listItems(conn, tableName));
            descriptor.setCharset(fileCharset);
            FileCreator.createFile(fileBuilder, descriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有子项组合
     *
     * @param conn      连接
     * @param tableName 表名
     * @return {@link List}
     * @throws SQLException
     */
    protected List<Item> listItems(Connection conn, String tableName) {
        List<Item> cols = new ArrayList<>();
        List<String> primaryKeys = getPrimaryKeys(conn, tableName);
        ResultSet columnRs = null;
        try {
            columnRs = conn.createStatement().executeQuery(String.format("Select * from %s", tableName));
            ResultSetMetaData metaData = columnRs.getMetaData();
            int index = 1;
            while (index++ < metaData.getColumnCount()) {

                String columnName = metaData.getColumnName(index);
                int columnSize = metaData.getColumnDisplaySize(index);
                boolean isNotNull = (ResultSetMetaData.columnNoNulls == metaData.isNullable(index));

                String columnType;
                if (userJavaType) {
                    columnType = TypeMap.getJavaType(metaData.getColumnType(index));
                } else {
                    columnType = metaData.getColumnTypeName(index);
                }
                Item item = new Item(columnType, columnName, nameConvertor.toFieldName(columnName));
                item.setLength(columnSize);
                item.setNotNull(isNotNull);
                item.setPK(isPrimaryKey(primaryKeys, columnName));
                cols.add(item);
            }
        } catch (SQLException e) {
            System.out.println(String.format("获取参数列失败... \n%s", e));
        } finally {
            DbUtils.closeQuietly(columnRs);
        }

        return cols;
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
            System.out.println(String.format("获取主键失败... \n%s", e));
        } finally {
            DbUtils.closeQuietly(keyRs);
        }
        return keyList;
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

    public static class DBOrmerBuilder {

        private static final String DEFAULT_PACKAGE_NAME = "com.qchery";

        private DBHelper dbHelper;
        private FileBuilder fileBuilder;
        private NameConvertor nameConvertor;
        private Charset charset;
        private boolean userJavaType;
        private String packageName;

        public DBOrmer build() {
            if (null == dbHelper || null == fileBuilder) {
                throw new ConfigException("dbHelper 与 FileBuilder 不能为空");
            }
            DBOrmer dbOrmer = new DBOrmer();
            dbOrmer.dbHelper = dbHelper;
            dbOrmer.fileBuilder = fileBuilder;
            dbOrmer.userJavaType = userJavaType;

            // 设置 NameConvertor
            if (null == nameConvertor) {
                nameConvertor = new DefaultNameConvertor();
            }
            dbOrmer.nameConvertor = nameConvertor;

            // 设置文件编码
            if (null == charset) {
                charset = Charset.defaultCharset();
            }
            dbOrmer.fileCharset = charset;

            // 设置包名
            if (null == packageName) {
                packageName = DEFAULT_PACKAGE_NAME;
            }
            dbOrmer.packageName = packageName;
            return dbOrmer;
        }

        public DBOrmerBuilder dbHelper(DBHelper dbHelper) {
            this.dbHelper = dbHelper;
            return this;
        }

        public DBOrmerBuilder fileBuilder(FileBuilder fileBuilder) {
            this.fileBuilder = fileBuilder;
            return this;
        }

        public DBOrmerBuilder nameConvertor(NameConvertor nameConvertor) {
            this.nameConvertor = nameConvertor;
            return this;
        }

        public DBOrmerBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public DBOrmerBuilder userJavaType(boolean userJavaType) {
            this.userJavaType = userJavaType;
            return this;
        }

        public DBOrmerBuilder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }
    }
}
