package com.qchery.kada.model.ibatis;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Test;

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
        resultMapTag.addId(id);
        resultMapTag.addResult(resultTag);
        mapperTag.setResultMapTag(resultMapTag);
        return mapperTag;
    }

}
