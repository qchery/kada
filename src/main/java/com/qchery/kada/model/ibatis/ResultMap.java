package com.qchery.kada.model.ibatis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class ResultMap {

    @XStreamAlias("id")
    @XStreamAsAttribute
    private String mapperId;

    @XStreamAsAttribute
    private String type;

    @XStreamImplicit(itemFieldName = "id")
    private Set<Result> ids = new HashSet<>();

    @XStreamImplicit
    private List<Result> results = new ArrayList<>();

    public ResultMap(String mapperId, String type) {
        super();
        this.mapperId = mapperId;
        this.type = type;
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

    public List<Result> getResults() {
        return results;
    }

    public void addResult(Result result) {
        this.results.add(result);
    }

    public Set<Result> getIds() {
        return this.ids;
    }

    public void addId(Result result) {
        this.ids.add(result);
    }
}
