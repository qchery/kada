package com.qchery.kada;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:20
 */
public class TableDescriptor {

    private String tableName;
    private String comment;
    private List<ColumnDescriptor> columnDescriptors = new ArrayList<>();

    public TableDescriptor(String tableName) {
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

    public List<ColumnDescriptor> getColumnDescriptors() {
        return columnDescriptors;
    }

    public void addColumnDescriptor(ColumnDescriptor columnDescriptor) {
        this.columnDescriptors.add(columnDescriptor);
    }

    public void addAll(List<ColumnDescriptor> columnDescriptors) {
        this.columnDescriptors.addAll(columnDescriptors);
    }

    @Override
    public String toString() {
        return "TableDescriptor{" +
                "tableName='" + tableName + '\'' +
                ", comment='" + comment + '\'' +
                ", columnDescriptors=" + columnDescriptors +
                '}';
    }
}
