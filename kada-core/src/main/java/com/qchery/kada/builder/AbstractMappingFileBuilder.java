package com.qchery.kada.builder;

import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

/**
 * @author Chery
 * @date 2018/4/15 15:37
 */
public abstract class AbstractMappingFileBuilder implements MappingFileBuilder {

    @Override
    public FileInfo build(FileInfo fileInfo, Mapping mapping) {
        fileInfo.setContent(getContent(fileInfo, mapping));
        fileInfo.setFileName(getFileName(mapping.getClassName()));
        fileInfo.setPackagePath(getFilePath(mapping.getPackageName()));
        return fileInfo;
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
     * @param fileInfo 文件信息
     * @param mapping  类与表的映射
     * @return {@link String}
     */
    protected abstract String getContent(FileInfo fileInfo, Mapping mapping);
}
