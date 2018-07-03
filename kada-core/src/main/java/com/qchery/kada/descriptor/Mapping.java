package com.qchery.kada.descriptor;

import com.qchery.kada.descriptor.db.TableInfo;
import com.qchery.kada.descriptor.java.IClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午11:20
 */
public class Mapping {

    private IClassInfo classInfo;

    private TableInfo tableInfo;

    private List<MappingItem> mappingItems = new ArrayList<>();

    private List<MappingItem> pkItems = new ArrayList<>();

    public Mapping(IClassInfo classInfo, TableInfo tableInfo) {
        this.classInfo = classInfo;
        this.tableInfo = tableInfo;
    }

    public void addMappingItem(MappingItem mappingItem) {
        if (mappingItem.isPK()) {
            pkItems.add(mappingItem);
        }
        mappingItems.add(mappingItem);
    }

    public IClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(IClassInfo classInfo) {
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

    public List<MappingItem> getPkItems() {
        return pkItems;
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
