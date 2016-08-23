package com.qchery.generate.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Clazz {
    
    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;
    
    @XStreamAlias("table")
    @XStreamAsAttribute
    private String table;

    @XStreamAlias("id")
    private Property id;
    
    @XStreamImplicit
    private List<Property> list = new ArrayList<>();

    public Clazz(String name, String table) {
        super();
        this.name = name;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Property getId() {
        return id;
    }

    public void setId(Property id) {
        this.id = id;
    }

    public List<Property> getList() {
        return list;
    }
    
    public void add(Property property) {
        this.list.add(property);
    }

}
