package com.qchery.generate.model.ibatis;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class ResultMap {

    @XStreamAlias("id")
    @XStreamAsAttribute
    private String mapperId;
    
    @XStreamAsAttribute
    private String type;
    
    @XStreamAlias("id")
    private Result id;
    
    @XStreamImplicit
    private List<Result> list = new ArrayList<>();

    public ResultMap(String mapperId, String type) {
        super();
        this.mapperId = mapperId;
        this.type = type;
    }

    public Result getId() {
        return id;
    }

    public void setId(Result id) {
        this.id = id;
    }

    public String getMapperId() {
        return mapperId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Result> getList() {
        return list;
    }

    public void add(Result result) {
        this.list.add(result);
    }
}
