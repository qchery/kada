package com.qchery.kada;

import com.qchery.kada.descriptor.db.TableInfo;
import com.qchery.kada.descriptor.java.ClassInfo;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午11:20
 */
public class Mapping {

    /**
     * 文件编码
     */
    private Charset charset;

    private ClassInfo classInfo;

    private TableInfo tableInfo;

    private List<MappingItem> mappingItems;

    public Mapping(ClassInfo classInfo, TableInfo tableInfo) {
        this.classInfo = classInfo;
        this.tableInfo = tableInfo;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<MappingItem> getMappingItems() {
        return mappingItems;
    }

    public void setMappingItems(List<MappingItem> mappingItems) {
        this.mappingItems = mappingItems;
    }

    public String getPackageName() {
        return classInfo.getPackageName();
    }

    public String getClassName() {
        return classInfo.getClassName();
    }

    public String getTableName() {
        return tableInfo.getTableName();
    }

    public String getTableComment() {
        String comment = tableInfo.getComment();
        if (null == comment || "".equals(comment)) {
            comment = tableInfo.getTableName();
        }
        return comment;
    }
}
