package com.qchery.kada.model.ibatis;

import com.qchery.kada.builder.mybatis.model.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class MapperTagTest {

    @Test
    public void toXML() {
        XStream xstream = new XStream(new DomDriver("utf8"));
        MapperTag createMapping = createMapper();
        xstream.processAnnotations(createMapping.getClass()); // 识别obj类中的注解
        System.out.println(xstream.toXML(createMapping));
    }

    private MapperTag createMapper() {
        MapperTag mapperTag = new MapperTag("cn.richinfo.cmail.antispam.dao.AsHoneypotDao");
        ResultMapTag resultMapTag = new ResultMapTag("FullResultMap", "asHoneypot");
        ResultTag id = new ResultTag("honeypotId", "honeypot_id");
        ResultTag resultTag = new ResultTag("emailAddr", "email_addr");

        InsertTag insertTag = new InsertTag();
        insertTag.setContent("insert into sys_user (id, name) values (#{id}, #{name})");
        insertTag.setId("insert");
        mapperTag.setInsertTags(Collections.singletonList(insertTag));

        UpdateTag updateTag =new UpdateTag();
        updateTag.setId("update");
        updateTag.setPrefix("update sys_user");
        updateTag.setSuffix("where id = #{id}");
        SetTag setTag = new SetTag();
        ArrayList<IfTag> ifTags = new ArrayList<>();
        ifTags.add(getIfTag("name = #{name},", "name != null"));
        ifTags.add(getIfTag("name = #{name},", "name != null"));
        setTag.setIfTags(ifTags);
        updateTag.setSetTag(setTag);
        mapperTag.setUpdateTags(Collections.singletonList(updateTag));

        resultMapTag.addId(id);
        resultMapTag.addResult(resultTag);
        mapperTag.setResultMapTag(resultMapTag);
        return mapperTag;
    }

    private IfTag getIfTag(String content, String test) {
        IfTag ifTag = new IfTag();
        ifTag.setContent(content);
        ifTag.setTest(test);
        return ifTag;
    }

}
