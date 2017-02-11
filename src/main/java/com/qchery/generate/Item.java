package com.qchery.generate;

/**
 * 生成Item的关键字
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:38
 */
public class Item {
    private String type;        // 类型
    private String columnName;        // 名称
    private String fieldName;   // 属性名
    private int length;         // 长度
    private boolean isPK;       // 是否为主键
    private boolean notNull;    // 可以为空?
    
    public Item(String type, String columnName, String fieldName) {
        this.type = type;
        this.columnName = columnName;
        this.fieldName = fieldName;
    }
    
    public String getType() {
        return type;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPK(boolean isPK) {
        this.isPK = isPK;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public int getLength() {
        return length;
    }

    public boolean isPK() {
        return isPK;
    }
    
    public boolean isNotNull() {
        return notNull;
    }
}
