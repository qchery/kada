package com.qchery.kada.model.ibatis;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("result")
public class ResultTag {

    @XStreamAsAttribute
    private String property;
    @XStreamAsAttribute
    private String column;

    public ResultTag(String property, String column) {
        super();
        this.property = property;
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
