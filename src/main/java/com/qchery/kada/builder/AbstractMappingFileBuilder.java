package com.qchery.kada.builder;

import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

import java.nio.charset.Charset;

/**
 * @author Chery
 * @date 2018/4/15 15:37
 */
public abstract class AbstractMappingFileBuilder implements MappingFileBuilder {

    @Override
    public FileInfo build(Mapping mapping) {
        String content = getContent(mapping);
        String fileName = getFileName(mapping.getClassName());
        String filePath = getFilePath(mapping.getPackageName());
        Charset charset = mapping.getCharset();
        return new FileInfo(filePath, fileName, content, charset);
    }

    protected String getFilePath(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    /**
     * 根据类名获取文件名
     *
     * @param className 类名
     * @return {@link String}
     */
    protected abstract String getFileName(String className);

    /**
     * 解析数据库关系为相应格式
     *
     * @param mapping 类与表的映射
     * @return {@link String}
     */
    protected abstract String getContent(Mapping mapping);
}
