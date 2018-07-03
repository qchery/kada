package com.qchery.kada.builder.hibernate;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

/**
 * Hibernate配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午8:47:06
 */
public class HibernateMappingFileBuilder extends AbstractMappingFileBuilder {

    private ContentBuilder contentBuilder;

    public HibernateMappingFileBuilder(ContentBuilder contentBuilder) {
        this.contentBuilder = contentBuilder;
    }

    @Override
    public String getContent(FileInfo fileInfo, Mapping mapping) {
        return contentBuilder.build(fileInfo.getCharset(), mapping);
    }

    @Override
    public String getFileName(String className) {
        return className + ".hbm.xml";
    }

}
