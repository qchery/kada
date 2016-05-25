package com.qchery.generate.hibernate.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("property")
public class Property {

    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;
    
    @XStreamAlias("type")
    @XStreamAsAttribute
    private String type;
    
    @XStreamAlias("column")
    private Column column;

    public Property(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
    
}
