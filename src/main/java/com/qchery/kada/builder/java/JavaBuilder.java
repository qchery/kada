package com.qchery.kada.builder.java;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.descriptor.Mapping;

/**
 * 映射生成Java类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:39
 */
public class JavaBuilder implements FileBuilder {

    private JavaContentBuilder contentBuilder;

    public JavaBuilder() {
        this.contentBuilder = new OriginalJavaContentBuilder();
    }

    public JavaBuilder(JavaContentBuilder contentBuilder) {
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
