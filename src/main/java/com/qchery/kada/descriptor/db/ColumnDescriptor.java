package com.qchery.kada.descriptor.db;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:21
 */
public class ColumnDescriptor {

    /**
     * 数据库字段类型
     */
    private int dbType;
    /**
     * 名称
     */
    private String columnName;
    /**
     * 长度
     */
    private int length;
    /**
     * 是否为主键
     */
    private boolean primaryKey;
    /**
     * 可以为空?
     */
    private boolean notNull;
    /**
     * 批注
     */
    private String comment;

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ColumnDescriptor{" +
                "dbType=" + dbType +
                ", columnName='" + columnName + '\'' +
                ", length=" + length +
                ", primaryKey=" + primaryKey +
                ", notNull=" + notNull +
                ", comment='" + comment + '\'' +
                '}';
    }
}
