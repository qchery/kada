package com.qchery.generate.model.hibernate;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Test;

public class HibernateMappingTest {
    
    public HibernateMapping createMapping() {
        Property id = new Property("id", "long");
        Property one = new Property("one", "int");
        Property two = new Property("two", "String");
        
        Clazz clazz = new Clazz("AdminLoginRecord", "RM_ADMIN_LOGIN_RECORD");
        clazz.setId(id);
        clazz.addProperty(one);
        clazz.addProperty(two);
        
        HibernateMapping mapping = new HibernateMapping("com.hmg.rmadmin.admin.bean");
        mapping.setClazz(clazz);
        return mapping;
    }

    @Test
    public void toXml() {
        XStream xstream = new XStream(new DomDriver("utf8"));
        HibernateMapping createMapping = createMapping();
        xstream.processAnnotations(createMapping.getClass()); // 识别obj类中的注解
        /*
         // 以压缩的方式输出XML
         StringWriter sw = new StringWriter();
         xstream.marshal(obj, new CompactWriter(sw));
         return sw.toString();
         */
        xstream.ignoreUnknownElements();
        // 以格式化的方式输出XML
         System.out.println(xstream.toXML(createMapping));
    }
    
}
