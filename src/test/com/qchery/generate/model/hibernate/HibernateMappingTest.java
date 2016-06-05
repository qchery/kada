package test.com.qchery.generate.model.hibernate;

import org.junit.Test;

import com.qchery.generate.model.hibernate.Clazz;
import com.qchery.generate.model.hibernate.Column;
import com.qchery.generate.model.hibernate.HibernateMapping;
import com.qchery.generate.model.hibernate.Property;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class HibernateMappingTest {
    
    public HibernateMapping createMapping() {
        Column column = new Column("columnName");
        
        Property id = new Property("id", "long");
        id.setColumn(column);
        
        Property one = new Property("one", "int");
        Property two = new Property("two", "String");
        
        Clazz clazz = new Clazz("AdminLoginRecord", "RM_ADMIN_LOGIN_RECORD");
        clazz.setId(id);
        clazz.add(one);
        clazz.add(two);
        
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
        // 以格式化的方式输出XML
         System.out.println(xstream.toXML(createMapping));
    }
    
}
