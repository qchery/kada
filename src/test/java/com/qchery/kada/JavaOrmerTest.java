package com.qchery.kada;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.builder.HibernateBuilder;
import com.qchery.kada.builder.MybatisBuilder;
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
