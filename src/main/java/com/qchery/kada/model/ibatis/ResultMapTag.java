package com.qchery.kada.model.ibatis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class ResultMapTag {

    @XStreamAlias("id")
    @XStreamAsAttribute
    private String mapperId;

    @XStreamAsAttribute
    private String type;

    @XStreamImplicit(itemFieldName = "id")
    private Set<ResultTag> ids = new HashSet<>();

    @XStreamImplicit
    private List<ResultTag> resultTags = new ArrayList<>();

    public ResultMapTag(String mapperId, String type) {
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

    public List<ResultTag> getResultTags() {
        return resultTags;
    }

    public void addResult(ResultTag resultTag) {
        this.resultTags.add(resultTag);
    }

    public Set<ResultTag> getIds() {
        return this.ids;
    }

    public void addId(ResultTag resultTag) {
        this.ids.add(resultTag);
    }
}
