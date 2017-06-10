package com.qchery.kada;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.builder.HibernateBuilder;
import com.qchery.kada.builder.MybatisBuilder;
import org.junit.Test;

import java.io.IOException;

public class JavaOrmerTest {

    @Test
    public void toIbatis() throws IOException {
        FileBuilder fileBuilder = new MybatisBuilder();
        JavaOrmer javaOrmer = new JavaOrmer(fileBuilder);
        javaOrmer.generateFile(ClassDescriptor.class);
    }

    @Test
    public void toHibernate() throws IOException {
        FileBuilder fileBuilder = new HibernateBuilder();
        JavaOrmer javaOrmer = new JavaOrmer(fileBuilder);
        javaOrmer.generateFile(ClassDescriptor.class);
    }

}
