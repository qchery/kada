package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.ContentBuilder;

/**
 * Mybatis配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:22
 */
public class MybatisMappingFileBuilder extends AbstractMybatisMappingFileBuilder {

    public static final String XML = "xml";

    public MybatisMappingFileBuilder(ContentBuilder contentBuilder) {
        super(contentBuilder);
    }

    @Override
    protected String getSuffix() {
        return XML;
    }

}
