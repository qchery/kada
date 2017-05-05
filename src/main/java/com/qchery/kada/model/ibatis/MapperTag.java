package com.qchery.kada.model.ibatis;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("mapper")
public class MapperTag {

    @XStreamAsAttribute
    private String namespace;
    
    @XStreamAlias("resultMap")
    private ResultMap resultMap;

    @XStreamImplicit
    private List<Insert> inserts;

    public MapperTag(String namespace) {
        super();
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    public List<Insert> getInserts() {
        return inserts;
    }

    public void setInserts(List<Insert> inserts) {
        this.inserts = inserts;
    }

}
