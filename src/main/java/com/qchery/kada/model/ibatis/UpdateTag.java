package com.qchery.kada.model.ibatis;

import com.qchery.kada.model.ibatis.converter.UpdateTagConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToStringConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;

/**
 * @author Chery
 * @date 2017/8/24 - 下午10:03
 */
@XStreamAlias("update")
@XStreamConverter(UpdateTagConverter.class)
public class UpdateTag {

    @XStreamAsAttribute
    private String id;

    private String prefix;

    @XStreamAlias("set")
    @XStreamConverter(ReflectionConverter.class)
    private SetTag setTag;

    @XStreamConverter(ToStringConverter.class)
    private String suffix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public SetTag getSetTag() {
        return setTag;
    }

    public void setSetTag(SetTag setTag) {
        this.setTag = setTag;
    }
}
