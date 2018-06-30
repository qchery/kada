package com.qchery.kada.builder;

import com.qchery.kada.descriptor.file.FileInfo;
import com.qchery.kada.descriptor.java.IClassInfo;

/**
 * @author Chery
 * @date 2018/4/14 16:41
 */
public interface ClassInfoFileBuilder {

    FileInfo getFileInfo(IClassInfo classInfo);

}
