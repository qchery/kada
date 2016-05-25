package com.qchery.generate.hibernate.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Column {

    @XStreamAsAttribute
    private String name;
    
    @XStreamAsAttribute
    @XStreamAlias("not-null")
    private boolean notNull;
    
    @XStreamAsAttribute
    private boolean unique;
    
    @XStreamAsAttribute
    @XStreamAlias("length")
    private int length;

    public Column(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
