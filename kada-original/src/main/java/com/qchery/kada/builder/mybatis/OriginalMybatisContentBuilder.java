package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.mybatis.model.*;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.utils.XMLUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Chery
 * @date 2018/4/15 11:11
 */
public class OriginalMybatisContentBuilder implements MybatisContentBuilder {
    @Override
    public String build(Mapping mapping) {
        MapperTag mapperTag = new MapperTag(String.format("%s.%sDao",
                mapping.getPackageName(), mapping.getClassName()));
        List<MappingItem> mappingItems = mapping.getMappingItems();
        mapperTag.setResultMapTag(getResultMap(mapping, mappingItems));
        mapperTag.setInsertTags(Collections.singletonList(getInsert(mapping, mappingItems)));
        mapperTag.setUpdateTags(Collections.singletonList(getUpdateSelective(mapping, mappingItems)));
        String content = XMLUtils.toXML(mapperTag);
        content = "<?xml version=\"1.0\" encoding=\"" + mapping.getCharset().name() + "\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + content;
        return content;
    }

    private ResultMapTag getResultMap(Mapping mapping, List<MappingItem> mappingItems) {
        ResultMapTag resultMapTag = new ResultMapTag("FullResultMap",
                mapping.getClassInfo().getType());
        for (MappingItem mappingItem : mappingItems) {
            ResultTag resultTag = new ResultTag(mappingItem.getFieldName(), mappingItem.getColumnName());
            if (mappingItem.isPK()) {
                resultMapTag.addId(resultTag);
            } else {
                resultMapTag.addResult(resultTag);
            }
        }
        return resultMapTag;
    }

    private InsertTag getInsert(Mapping mapping, List<MappingItem> mappingItems) {
        StringBuilder insertContentBuilder = new StringBuilder("\ninsert into ").append(mapping.getTableName()).append("(");
        StringBuilder valuesBuilder = new StringBuilder();
        for (int i = 0; i < mappingItems.size(); i++) {
            MappingItem mappingItem = mappingItems.get(i);
            insertContentBuilder.append("\n").append(mappingItem.getColumnName());
            valuesBuilder.append("\n#{").append(mappingItem.getFieldName()).append("}");
            if (i != mappingItems.size() - 1) {
                insertContentBuilder.append(",");
                valuesBuilder.append(",");
            } else {
                insertContentBuilder.append("\n) values (");
                valuesBuilder.append("\n)\n");
            }
        }
        InsertTag insertTag = new InsertTag();
        insertTag.setId("insert");
        insertTag.setContent(insertContentBuilder.append(valuesBuilder).toString());
        return insertTag;
    }

    private UpdateTag getUpdateSelective(Mapping mapping, List<MappingItem> mappingItems) {
        UpdateTag updateTag = new UpdateTag();
        updateTag.setId("updateSelective");
        updateTag.setPrefix("UPDATE " + mapping.getTableName() + " SET ");
        SetTag setTag = new SetTag();
        ArrayList<IfTag> ifTags = new ArrayList<>();
        for (int i = 0; i < mappingItems.size(); i++) {
            MappingItem item = mappingItems.get(i);
            IfTag ifTag = new IfTag();
            ifTag.setTest("null != " + item.getFieldName());
            String content = item.getColumnName() + " = #{" + item.getFieldName() + "}";
            if (i != mappingItems.size() - 1) {
                content += ",";
            }
            ifTag.setContent(content);
            ifTags.add(ifTag);
        }
        setTag.setIfTags(ifTags);
        updateTag.setSetTag(setTag);

        StringBuilder whereBuilder = new StringBuilder("WHERE ");
        boolean isFirstPk = true;
        for (MappingItem item : mappingItems) {
            if (item.isPK()) {
                if (!isFirstPk) {
                    whereBuilder.append(" AND ");
                } else {
                    isFirstPk = false;
                }
                whereBuilder.append(item.getColumnName());
                whereBuilder.append(" = #{");
                whereBuilder.append(item.getFieldName());
                whereBuilder.append("}");
            }
        }
        updateTag.setSuffix(whereBuilder.toString());

        return updateTag;
    }
}
