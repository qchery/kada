package com.qchery.kada.builder;

import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

import java.nio.charset.Charset;

/**
 * 文件建造器
 *
 * @author Chery
 * @date 2015年2月27日-下午4:17:17
 */
public interface FileBuilder {

    /**
     * 根据类名获取文件名
     *
     * @param className 类名
     * @return {@link String}
     */
    String getFileName(String className);

    /**
     * 解析数据库关系为相应格式
     *
     * @param mapping 类与表的映射
     * @return {@link String}
     */
    String getContent(Mapping mapping);

    default FileInfo getFileInfo(Mapping mapping) {
        String content = getContent(mapping);
        String fileName = getFileName(mapping.getClassName());
        String packagePath = mapping.getPackageName().replaceAll("\\.", "/");
        Charset charset = mapping.getCharset();
        return new FileInfo(packagePath, fileName, content, charset);
    }

}
