package com.qchery.kada.builder.java;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.java.IClassInfo;

/**
 * 映射生成Java类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaMappingFileBuilder extends AbstractMappingFileBuilder {

    public JavaMappingFileBuilder(ContentBuilder contentBuilder) {
        super(contentBuilder);
    }

    @Override
    public String getFileName(IClassInfo classInfo) {
        return classInfo.getClassName() + ".java";
    }

    @Override
    protected String getPackageName(IClassInfo classInfo) {
        return classInfo.toEntityPackage();
    }
}
