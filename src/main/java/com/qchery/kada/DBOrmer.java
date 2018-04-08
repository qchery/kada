package com.qchery.kada;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.db.DBHelper;
import com.qchery.kada.db.TypeMap;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.descriptor.db.ColumnInfo;
import com.qchery.kada.descriptor.db.TableInfo;
import com.qchery.kada.descriptor.java.ClassInfo;
import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.TypeInfo;
import com.qchery.kada.exception.ConfigException;
import com.qchery.kada.filter.TableNameFilter;
import com.qchery.kada.scanner.DBScanner;
import com.qchery.kada.scanner.DefaultDBScanner;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
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

    private DBHelper dbHelper;    // 支持多数据库
    private NameConvertor nameConvertor;
    private FileBuilder fileBuilder;
    private TableNameFilter tableNameFilter;
    private DBScanner dbScanner = new DefaultDBScanner();
    private Charset fileCharset;
    private String packageName;

    private Logger logger = LoggerFactory.getLogger(DBOrmer.class);

    private DBOrmer() {
    }

    /**
     * 将数据库字段转换成对应的Java文件
     */
    public void generateFile() {
        Connection conn = null;
        try {
            conn = dbHelper.getConnection();
            List<TableInfo> tableInfos = dbScanner.scannerTables(conn);

            if (tableNameFilter != null) {
                tableInfos.removeIf(tableInfo -> !tableNameFilter.accept(tableInfo.getTableName()));
            }

            for (TableInfo tableInfo : tableInfos) {
                generateFile(conn, tableInfo);
            }

        } catch (SQLException e) {
            logger.error("msg={}", "数据库链接获取失败", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 将数据库表字段转换成对就的JAVA代码
     *
     * @param tableName 表名
     */
    public void generateFile(String tableName) {
        Connection conn = null;
        try {
            conn = dbHelper.getConnection();
            generateFile(conn, new TableInfo(tableName));
        } catch (SQLException e) {
            logger.error("msg={}", "数据库链接获取失败", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    private void generateFile(Connection conn, TableInfo tableInfo) {
        try {
            Mapping mapping = getMapping(conn, tableInfo);
            FileCreator.createFile(fileBuilder.getFileInfo(mapping));
        } catch (IOException e) {
            logger.error("msg={}", "文件生成失败", e);
        }
    }

    private Mapping getMapping(Connection conn, TableInfo tableInfo) {
        List<ColumnInfo> columnInfos = dbScanner.scannerColumns(conn, tableInfo.getTableName());
        tableInfo.addAll(columnInfos);
        String className = nameConvertor.toClassName(tableInfo.getTableName());
        ClassInfo classInfo = ClassInfo.of(packageName, className);

        ArrayList<MappingItem> mappingItems = new ArrayList<>();
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            TypeInfo javaType = TypeMap.getJavaType(columnInfo.getDbType());
            String fieldName = nameConvertor.toFieldName(columnInfo.getColumnName());
            FieldInfo fieldInfo = new FieldInfo(ClassInfo.of(javaType), fieldName);
            classInfo.addFieldInfo(fieldInfo);
            mappingItems.add(new MappingItem(fieldInfo, columnInfo));
        }

        Mapping mapping = new Mapping(classInfo, tableInfo);
        mapping.setMappingItems(mappingItems);
        mapping.setCharset(fileCharset);
        return mapping;
    }

    public static class DBOrmerBuilder {

        private static final String DEFAULT_PACKAGE_NAME = "com.qchery";

        private DBHelper dbHelper;
        private FileBuilder fileBuilder;
        private NameConvertor nameConvertor;
        private TableNameFilter tableNameFilter;
        private Charset charset;
        private String packageName;

        public DBOrmer build() {
            if (null == dbHelper || null == fileBuilder) {
                throw new ConfigException("dbHelper 与 FileBuilder 不能为空");
            }
            DBOrmer dbOrmer = new DBOrmer();
            dbOrmer.dbHelper = dbHelper;
            dbOrmer.fileBuilder = fileBuilder;
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
