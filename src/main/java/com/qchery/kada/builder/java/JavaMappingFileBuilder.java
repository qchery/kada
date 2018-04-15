package com.qchery.kada.builder.java;

import com.qchery.kada.builder.MappingFileBuilder;
import com.qchery.kada.descriptor.Mapping;

/**
 * 映射生成Java类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaMappingFileBuilder implements MappingFileBuilder {

    private JavaContentBuilder contentBuilder;

    public JavaMappingFileBuilder() {
        this.contentBuilder = new TemplateJavaContentBuilder();
    }

    public JavaMappingFileBuilder(JavaContentBuilder contentBuilder) {
        this.contentBuilder = contentBuilder;
    }

    @Override
    public String getContent(Mapping mapping) {
        return contentBuilder.build(mapping.getClassInfo());
    }

    @Override
    public String getFileName(String className) {
        return className + ".java";
    }

}
