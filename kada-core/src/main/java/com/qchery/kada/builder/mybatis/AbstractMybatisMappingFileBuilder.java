package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.java.IClassInfo;

/**
 * @author Chery
 * @date 2018/8/22 21:16
 */
public abstract class AbstractMybatisMappingFileBuilder extends AbstractMappingFileBuilder {

    public AbstractMybatisMappingFileBuilder(ContentBuilder contentBuilder) {
        super(contentBuilder);
    }

    @Override
    protected String getFileName(IClassInfo classInfo) {
        return classInfo.toDaoClassName() + "." + getSuffix();
    }

    protected abstract String getSuffix();

    @Override
    protected String getPackageName(IClassInfo classInfo) {
        return classInfo.toDaoPackage();
    }
}
