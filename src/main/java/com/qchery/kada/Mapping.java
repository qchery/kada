package com.qchery.kada;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午11:20
 */
public class Mapping {

    private ClassDescriptor classDescriptor;

    private TableDescriptor tableDescriptor;

    private List<MappingItem> mappingItems;

    public Mapping(ClassDescriptor classDescriptor, TableDescriptor tableDescriptor) {
        this.classDescriptor = classDescriptor;
        this.tableDescriptor = tableDescriptor;
    }

    public ClassDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    public void setClassDescriptor(ClassDescriptor classDescriptor) {
        this.classDescriptor = classDescriptor;
    }

    public TableDescriptor getTableDescriptor() {
        return tableDescriptor;
    }

    public void setTableDescriptor(TableDescriptor tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
    }

    public List<MappingItem> getMappingItems() {
        return mappingItems;
    }

    public void setMappingItems(List<MappingItem> mappingItems) {
        this.mappingItems = mappingItems;
    }

    public String getPackageName() {
        return classDescriptor.getPackageName();
    }

    public String getClassName() {
        return classDescriptor.getClassName();
    }

    public String getTableName() {
        return tableDescriptor.getTableName();
    }

    public Charset getCharset() {
        return classDescriptor.getCharset();
    }
}
