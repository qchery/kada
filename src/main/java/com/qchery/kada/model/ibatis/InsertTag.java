package com.qchery.kada.model.ibatis;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * @author Chery
 * @date 2017/5/5 - 下午10:38
 */
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"content"})
@XStreamAlias("insert")
public class InsertTag {

    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
