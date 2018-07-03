package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

import java.io.File;

/**
 * Mybatis配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:22
 */
public class MybatisMappingFileBuilder extends AbstractMappingFileBuilder {

    private ContentBuilder contentBuilder;

    public MybatisMappingFileBuilder(ContentBuilder contentBuilder) {
        this.contentBuilder = contentBuilder;
    }

    @Override
    public String getContent(FileInfo fileInfo, Mapping mapping) {
        return contentBuilder.build(fileInfo.getCharset(), mapping);
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
