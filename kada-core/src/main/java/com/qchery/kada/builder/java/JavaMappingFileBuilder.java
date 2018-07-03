package com.qchery.kada.builder.java;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

/**
 * 映射生成Java类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaMappingFileBuilder extends AbstractMappingFileBuilder {

    private ContentBuilder contentBuilder;

    public JavaMappingFileBuilder(ContentBuilder contentBuilder) {
        this.contentBuilder = contentBuilder;
    }

    @Override
    public String getContent(FileInfo fileInfo, Mapping mapping) {
        return contentBuilder.build(fileInfo.getCharset(), mapping);
    }

    @Override
    public String getFileName(String className) {
        return className + ".java";
    }

}
