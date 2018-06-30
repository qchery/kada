package com.qchery.kada;

import com.qchery.kada.builder.MappingFileBuilder;
import com.qchery.kada.builder.hibernate.HibernateMappingFileBuilder;
import com.qchery.kada.builder.hibernate.OriginalHibernateContentBuilder;
import com.qchery.kada.builder.mybatis.MybatisMappingFileBuilder;
import com.qchery.kada.builder.mybatis.TemplateMybatisContentBuilder;
import com.qchery.kada.descriptor.java.ClassInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class JavaOrmerTest {

    private String rootPath;

    @Before
    public void init() {
        rootPath = System.getProperty("user.dir");
    }

    @Test
    public void toMybatis() throws IOException {
        MappingFileBuilder mappingFileBuilder = new MybatisMappingFileBuilder(new TemplateMybatisContentBuilder());
        JavaOrmer javaOrmer = new JavaOrmer(rootPath, mappingFileBuilder);
        javaOrmer.generateFile(ClassInfo.class);
    }

    @Test
    public void toHibernate() throws IOException {
        MappingFileBuilder mappingFileBuilder = new HibernateMappingFileBuilder(new OriginalHibernateContentBuilder());
        JavaOrmer javaOrmer = new JavaOrmer(rootPath, mappingFileBuilder);
        javaOrmer.generateFile(ClassInfo.class);
    }

}
