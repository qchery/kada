package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.descriptor.file.FileInfo;
import com.qchery.kada.utils.StringUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chery
 * @date 2018/7/3 21:21
 */
public class MybatisDaoMappingFileBuilder extends AbstractMappingFileBuilder {

    @Override
    protected String getFileName(String className) {
        return className + "Dao.java";
    }

    @Override
    protected String getContent(FileInfo fileInfo, Mapping mapping) {
        StringBuilder builder = new StringBuilder("package ").append(mapping.getPackageName()).append(".dao;\n\n");
        builder.append("import ").append(mapping.getPackageName()).append(".").append(mapping.getClassName()).append(";\n\n");

        builder.append("public interface ").append(mapping.getClassName()).append("Dao {\n\n");
        // 查询方法
        builder.append("    ").append(mapping.getClassName()).append(" getById(");
        List<MappingItem> pkItems = mapping.getPkItems();
        Iterator<MappingItem> iterator = pkItems.iterator();
        while (iterator.hasNext()) {
            MappingItem item = iterator.next();
            builder.append(item.getSimpleJavaType()).append(" ")
                    .append(StringUtils.lowerFirstChar(item.getSimpleJavaType()));
            if (iterator.hasNext()) {
                builder.append(", ");
            } else {
                builder.append(");\n\n");
            }
        }
        // 插入方法
        builder.append("    int insert(").append(mapping.getClassName()).append(" ")
                .append(StringUtils.lowerFirstChar(mapping.getClassName())).append(");\n\n");
        // 更新方法
        builder.append("    int update(").append(mapping.getClassName()).append(" ")
                .append(StringUtils.lowerFirstChar(mapping.getClassName())).append(");\n\n");

        builder.append("}");
        return builder.toString();
    }

    @Override
    protected String getFilePath(String packageName) {
        return super.getFilePath(packageName) + File.separatorChar + "dao";
    }
}
