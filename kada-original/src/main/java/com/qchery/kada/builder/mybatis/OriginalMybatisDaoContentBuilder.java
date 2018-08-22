package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.utils.StringUtils;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chery
 * @date 2018/8/22 21:29
 */
public class OriginalMybatisDaoContentBuilder implements ContentBuilder {

    @Override
    public String build(Charset charset, Mapping mapping) {
        StringBuilder builder = new StringBuilder("package ").append(mapping.toDaoPackage()).append(";\n\n");
        builder.append("import ").append(mapping.getType()).append(";\n\n");

        builder.append("public interface ").append(mapping.toDaoClassName()).append(" {\n\n");
        // 查询方法
        builder.append("    ").append(mapping.getClassName()).append(" getById(");
        List<MappingItem> pkItems = mapping.getPkItems();
        Iterator<MappingItem> iterator = pkItems.iterator();
        while (iterator.hasNext()) {
            MappingItem item = iterator.next();
            builder.append(item.getSimpleJavaType()).append(" ")
                    .append(StringUtils.lowerFirstChar(item.getFieldName()));
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
        builder.append("    int updateSelective(").append(mapping.getClassName()).append(" ")
                .append(StringUtils.lowerFirstChar(mapping.getClassName())).append(");\n\n");

        builder.append("}");
        return builder.toString();
    }

}
