package com.qchery.kada.model.ibatis;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author Chery
 * @date 2017/8/24 - 下午10:07
 */
@XStreamAlias("set")
public class SetTag {

    @XStreamImplicit
    private List<IfTag> ifTags;

    public List<IfTag> getIfTags() {
        return ifTags;
    }

    public void setIfTags(List<IfTag> ifTags) {
        this.ifTags = ifTags;
    }
}
