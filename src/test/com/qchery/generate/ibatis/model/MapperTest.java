package test.com.qchery.generate.ibatis.model;

import org.junit.Test;

import com.qchery.generate.ibatis.model.Mapper;
import com.qchery.generate.ibatis.model.Result;
import com.qchery.generate.ibatis.model.ResultMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MapperTest {

    @Test
    public void toXML() {
        XStream xstream = new XStream(new DomDriver("utf8"));
        Mapper createMapping = createMapper();
        xstream.processAnnotations(createMapping.getClass()); // 识别obj类中的注解
        System.out.println(xstream.toXML(createMapping));
    }

    private Mapper createMapper() {
        Mapper mapper = new Mapper("cn.richinfo.cmail.antispam.dao.AsHoneypotDao");
        ResultMap resultMap = new ResultMap("FullResultMap", "asHoneypot");
        Result id = new Result("honeypotId", "honeypot_id");
        Result result = new Result("emailAddr", "email_addr");
        resultMap.setId(id);
        resultMap.add(result);
        mapper.setResultMap(resultMap);
        return mapper;
    }

}
