package com.qchery.kada;

import com.qchery.kada.descriptor.db.ColumnInfo;
import com.qchery.kada.descriptor.java.FieldInfo;

/**
 * @author Chery
 * @date 2017/5/4 - 下午11:21
 */
public class MappingItem {

    private FieldInfo fieldInfo;
    private ColumnInfo columnInfo;

    public MappingItem(FieldInfo fieldInfo, ColumnInfo columnInfo) {
        this.fieldInfo = fieldInfo;
        this.columnInfo = columnInfo;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }

    public String getJavaType() {
        return fieldInfo.getType();
    }

    public String getFieldName() {
        return fieldInfo.getFieldName();
    }

    public boolean isPK() {
        return columnInfo.isPrimaryKey();
    }

    public String getColumnName() {
        return columnInfo.getColumnName();
    }

    public int getLength() {
        return columnInfo.getLength();
    }

    public boolean isNotNull() {
        return columnInfo.isNotNull();
    }

    public String getComment() {
        return columnInfo.getComment();
    }

    public String getSimpleJavaType() {
        return fieldInfo.getSimpleType();
    }
}
