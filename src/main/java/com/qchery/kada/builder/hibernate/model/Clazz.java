package com.qchery.kada.builder.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Clazz {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String table;

    /**
     * 单一主键
     */
    private Property id;

    /**
     * 联合主键
     */
    @XStreamAlias("composite-id")
    private CompositeId compositeId;

    @XStreamImplicit
    private List<Property> properties = new ArrayList<>();

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

    public List<Property> getProperties() {
        return properties;
    }

    public void addProperty(Property property) {
        this.properties.add(property);
    }

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId compositeId) {
        this.compositeId = compositeId;
    }
}
