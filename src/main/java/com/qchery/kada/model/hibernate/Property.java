package com.qchery.kada.model.hibernate;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("property")
public class Property {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String type;

    @XStreamAsAttribute
    private String column;

    @XStreamAsAttribute
    @XStreamAlias("not-null")
    private Boolean notNull;

    @XStreamAsAttribute
    private Boolean unique;

    @XStreamAsAttribute
    private Integer length;

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

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
