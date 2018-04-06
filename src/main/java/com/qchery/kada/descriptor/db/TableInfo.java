package com.qchery.kada.descriptor.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:20
 */
public class TableInfo {

    private String tableName;
    private String comment;
    private List<ColumnInfo> columnInfos = new ArrayList<>();

    public TableInfo(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void addColumnInfo(ColumnInfo columnInfo) {
        this.columnInfos.add(columnInfo);
    }

    public void addAll(List<ColumnInfo> columnInfos) {
        this.columnInfos.addAll(columnInfos);
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableName='" + tableName + '\'' +
                ", comment='" + comment + '\'' +
                ", columnInfos=" + columnInfos +
                '}';
    }
}
