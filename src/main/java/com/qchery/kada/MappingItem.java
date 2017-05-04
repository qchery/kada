package com.qchery.kada;

/**
 * @author Chery
 * @date 2017/5/4 - 下午11:21
 */
public class MappingItem {

    private FieldDescriptor fieldDescriptor;
    private ColumnDescriptor columnDescriptor;

    public MappingItem(FieldDescriptor fieldDescriptor, ColumnDescriptor columnDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
        this.columnDescriptor = columnDescriptor;
    }

    public FieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public ColumnDescriptor getColumnDescriptor() {
        return columnDescriptor;
    }

    public void setColumnDescriptor(ColumnDescriptor columnDescriptor) {
        this.columnDescriptor = columnDescriptor;
    }

    public String getJavaType() {
        return fieldDescriptor.getType();
    }

    public String getFieldName() {
        return fieldDescriptor.getFieldName();
    }

    public boolean isPK() {
        return columnDescriptor.isPrimaryKey();
    }

    public String getColumnName() {
        return columnDescriptor.getColumnName();
    }

    public int getLength() {
        return columnDescriptor.getLength();
    }

    public boolean isNotNull() {
        return columnDescriptor.isNotNull();
    }
}
