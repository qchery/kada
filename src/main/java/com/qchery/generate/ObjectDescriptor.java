package com.qchery.generate;

import java.nio.charset.Charset;
import java.util.List;

public class ObjectDescriptor {

    // 文件编码
    private Charset charset;
    // 包名
    private String packageName;
    // 类名
    private String className;
    // 表名
    private String tableName;
    // 字段
    private List<Item> items;

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
