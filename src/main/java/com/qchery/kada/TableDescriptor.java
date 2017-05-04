package com.qchery.kada;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:20
 */
public class TableDescriptor {

    private String tableName;
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

    public List<ColumnDescriptor> getColumnDescriptors() {
        return columnDescriptors;
    }

    public void addColumnDescriptor(ColumnDescriptor columnDescriptor) {
        this.columnDescriptors.add(columnDescriptor);
    }

    @Override
    public String toString() {
        return "TableDescriptor{" +
                "tableName='" + tableName + '\'' +
                ", columnDescriptors=" + columnDescriptors +
                '}';
    }
}
