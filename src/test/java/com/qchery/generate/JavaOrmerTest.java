package com.qchery.generate;

import com.qchery.generate.builder.FileBuilder;
import com.qchery.generate.builder.HibernateBuilder;
import com.qchery.generate.builder.MybatisBuilder;
import org.junit.Test;

import java.io.IOException;

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
