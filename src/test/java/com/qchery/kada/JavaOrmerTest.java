package com.qchery.kada;

import com.qchery.kada.builder.MappingFileBuilder;
import com.qchery.kada.builder.HibernateMappingFileBuilder;
import com.qchery.kada.builder.mybatis.MybatisMappingFileBuilder;
import com.qchery.kada.descriptor.java.ClassInfo;
import org.junit.Test;

import java.io.IOException;

public class JavaOrmerTest {

    @Test
    public void toIbatis() throws IOException {
        MappingFileBuilder mappingFileBuilder = new MybatisMappingFileBuilder();
        JavaOrmer javaOrmer = new JavaOrmer(mappingFileBuilder);
        javaOrmer.generateFile(ClassInfo.class);
    }

    @Test
    public void toHibernate() throws IOException {
        MappingFileBuilder mappingFileBuilder = new HibernateMappingFileBuilder();
        JavaOrmer javaOrmer = new JavaOrmer(mappingFileBuilder);
        javaOrmer.generateFile(ClassInfo.class);
    }

}
