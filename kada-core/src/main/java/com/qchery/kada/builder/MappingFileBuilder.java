package com.qchery.kada.builder;

import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.file.FileInfo;

/**
 * 文件建造器
 *
 * @author Chery
 * @date 2015年2月27日-下午4:17:17
 */
public interface MappingFileBuilder {

    FileInfo build(FileInfo fileInfo, Mapping mapping);

}
