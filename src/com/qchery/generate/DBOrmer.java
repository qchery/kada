package com.qchery.generate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.qchery.generate.builder.FileBuilder;
import com.qchery.generate.convertor.DefaultNameConvertor;
import com.qchery.generate.convertor.NameConvertor;
import com.qchery.generate.db.DBHelper;
import com.qchery.generate.db.Global;
import com.qchery.generate.db.TypeMap;

/**
 * 将数据表属性列转换在Java对象属性的工具类
 * @author chinrui1016@163.com
 * @date 2016年5月15日 - 下午7:55:37
 */
public class DBOrmer {
    
    public static final String COLUMN_NAME = "COLUMN_NAME";
    
    private DBHelper dbHelper;    // 支持多数据库
    private NameConvertor nameConvertor;
    private FileBuilder fileBuilder;
    
    public DBOrmer(DBHelper dbHelper, FileBuilder fileBuilder) {
        Global.initConfig();
        this.dbHelper = dbHelper;
        this.nameConvertor = new DefaultNameConvertor();
        this.fileBuilder = fileBuilder;
    }
    
    /**
     * 将数据库字段转换成对应的Java文件
     * @param cp
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
     * @param cp
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
            descriptor.setPackageName(Global.getProperty("package.name"));
            descriptor.setClassName(nameConvertor.toClassName(tableName));
            descriptor.setTableName(tableName);
            descriptor.setItems(listItems(conn, tableName));
            FileCreator.createFile(fileBuilder, descriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取所有子项组合
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
                
                String columnType = null;
                if (Global.isUseJavaType()) {
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
     * @param conn          获取表中的所有主键名称
     * @param tableName     表名
     * @return
     * @throws SQLException
     */
    private List<String> getPrimaryKeys(Connection conn, String tableName) {
        ResultSet keyRs = null;
        List<String> keyList = new ArrayList<>();
        try {
            keyRs = conn.getMetaData().getPrimaryKeys(conn.getCatalog(), "%", tableName);
            
            while ( keyRs.next() ) {
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
     * @param keys       主键属性集
     * @param columnName       当前属性
     * @return
     * @throws SQLException
     */
    private boolean isPrimaryKey(List<String> keys, String columnName) {
        boolean result = false;
        for ( String key : keys ) {
            if ( key.equals(columnName) ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void setFileBuilder(FileBuilder fileBuilder) {
        this.fileBuilder = fileBuilder;
    }
    
}
