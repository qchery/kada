package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.descriptor.Mapping;

import java.io.File;

/**
 * Mybatis配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:22
 */
public class MybatisMappingFileBuilder extends AbstractMappingFileBuilder {

    private MybatisContentBuilder mybatisContentBuilder;

    public MybatisMappingFileBuilder() {
        this.mybatisContentBuilder = new TemplateMybatisContentBuilder();
    }

    public MybatisMappingFileBuilder(MybatisContentBuilder mybatisContentBuilder) {
        this.mybatisContentBuilder = mybatisContentBuilder;
    }

    @Override
    public String getContent(Mapping mapping) {
        return mybatisContentBuilder.build(mapping);
    }


    @Override
    public String getFileName(String className) {
        return className + "Dao.xml";
    }

    @Override
    public String getFilePath(String packageName) {
        return super.getFilePath(packageName) + File.separatorChar + "dao";
    }
}
