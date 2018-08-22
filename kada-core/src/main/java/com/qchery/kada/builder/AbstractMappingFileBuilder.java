package com.qchery.kada.builder;

import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;
import com.qchery.kada.descriptor.java.IClassInfo;

/**
 * @author Chery
 * @date 2018/4/15 15:37
 */
public abstract class AbstractMappingFileBuilder implements MappingFileBuilder {

    protected ContentBuilder contentBuilder;

    public AbstractMappingFileBuilder(ContentBuilder contentBuilder) {
        this.contentBuilder = contentBuilder;
    }

    @Override
    public FileInfo build(FileInfo fileInfo, Mapping mapping) {
        fileInfo.setContent(getContent(fileInfo, mapping));
        fileInfo.setFileName(getFileName(mapping.getClassInfo()));
        fileInfo.setPackagePath(getFilePath(mapping.getClassInfo()));
        return fileInfo;
    }

    private String getFilePath(IClassInfo classInfo) {
        return getPackageName(classInfo).replaceAll("\\.", "/");
    }

    protected String getPackageName(IClassInfo classInfo) {
        return classInfo.getPackageName();
    }

    /**
     * 根据类名获取文件名
     *
     * @param classInfo 类信息
     * @return {@link String}
     */
    protected abstract String getFileName(IClassInfo classInfo);

    protected String getContent(FileInfo fileInfo, Mapping mapping) {
        return contentBuilder.build(fileInfo.getCharset(), mapping);
    }
}
