package test.com.qchery.generate;

import java.io.IOException;

import org.junit.Test;

import com.qchery.generate.JavaOrmer;
import com.qchery.generate.ObjectDescriptor;
import com.qchery.generate.builder.FileBuilder;
import com.qchery.generate.builder.HibernateBuilder;
import com.qchery.generate.builder.MybatisBuilder;

public class JavaOrmerTest {
    
    @Test
    public void toIbatis() {
        FileBuilder fileBuilder = new MybatisBuilder();
        JavaOrmer javaOrmer = new JavaOrmer(fileBuilder);
        try {
            javaOrmer.generateFile(ObjectDescriptor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void toHibernate() {
        FileBuilder fileBuilder = new HibernateBuilder();
        JavaOrmer javaOrmer = new JavaOrmer(fileBuilder);
        try {
            javaOrmer.generateFile(ObjectDescriptor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
