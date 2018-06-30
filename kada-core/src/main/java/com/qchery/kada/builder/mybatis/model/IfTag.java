package com.qchery.kada.builder.mybatis.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * @author Chery
 * @date 2017/8/24 - 下午10:08
 */
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"content"})
@XStreamAlias("if")
public class IfTag {

    @XStreamAsAttribute
    private String test;

    private String content;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
