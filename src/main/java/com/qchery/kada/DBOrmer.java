package com.qchery.kada;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.db.DBHelper;
import com.qchery.kada.db.TypeMap;
import com.qchery.kada.exception.ConfigException;
import com.qchery.kada.filter.TableNameFilter;
import com.qchery.kada.scanner.DefaultDBScanner;
import org.apache.commons.dbutils.DbUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 将数据表属性列转换在Java对象属性的工具类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:55:37
 */
public class DBOrmer {

    private DBHelper dbHelper;    // 支持多数据库
    private NameConvertor nameConvertor;
    private FileBuilder fileBuilder;
    private TableNameFilter tableNameFilter;
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

            List<String> tableNames = new ArrayList<>();
            while (rs.next()) {
                tableNames.add(rs.getString(1));

            }

            if (tableNameFilter != null) {
                Iterator<String> iterator = tableNames.iterator();
                while (iterator.hasNext()) {
                    if (!tableNameFilter.accept(iterator.next())) {
                        iterator.remove();
                    }
                }
            }

            for (String tableName : tableNames) {
                generateFile(conn, tableName);
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
            TableDescriptor tableDescriptor = new DefaultDBScanner().scannerTable(conn, tableName);

            ClassDescriptor classDescriptor = new ClassDescriptor();
            classDescriptor.setPackageName(packageName);
            classDescriptor.setClassName(nameConvertor.toClassName(tableName));

            ArrayList<MappingItem> mappingItems = new ArrayList<>();
            for (ColumnDescriptor columnDescriptor : tableDescriptor.getColumnDescriptors()) {
                String javaType = TypeMap.getJavaType(columnDescriptor.getDbType());
                String fieldName = nameConvertor.toFieldName(columnDescriptor.getColumnName());
                FieldDescriptor fieldDescriptor = new FieldDescriptor(javaType, fieldName);
                classDescriptor.addFieldDescriptor(fieldDescriptor);
                mappingItems.add(new MappingItem(fieldDescriptor, columnDescriptor));
            }
            classDescriptor.setCharset(fileCharset);

            Mapping mapping = new Mapping(classDescriptor, tableDescriptor);
            mapping.setMappingItems(mappingItems);

            FileCreator.createFile(fileBuilder, mapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DBOrmerBuilder {

        private static final String DEFAULT_PACKAGE_NAME = "com.qchery";

        private DBHelper dbHelper;
        private FileBuilder fileBuilder;
        private NameConvertor nameConvertor;
        private TableNameFilter tableNameFilter;
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
            dbOrmer.tableNameFilter = tableNameFilter;

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

        public DBOrmerBuilder tableNameFilter(TableNameFilter tableNameFilter) {
            this.tableNameFilter = tableNameFilter;
            return this;
        }
    }
}
