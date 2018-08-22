package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.ContentBuilder;

/**
 * @author Chery
 * @date 2018/7/3 21:21
 */
public class MybatisDaoMappingFileBuilder extends AbstractMybatisMappingFileBuilder {

    public static final String JAVA = "java";

    public MybatisDaoMappingFileBuilder(ContentBuilder contentBuilder) {
        super(contentBuilder);
    }

    @Override
    protected String getSuffix() {
        return JAVA;
    }

}
