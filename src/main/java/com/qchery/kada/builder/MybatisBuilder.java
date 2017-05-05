package com.qchery.kada.builder;

import java.util.Collections;
import java.util.List;

import com.qchery.kada.*;
import com.qchery.kada.model.ibatis.*;
import com.qchery.kada.utils.StringUtil;
import com.qchery.kada.utils.XMLUtil;

/**
 * Mybatis配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:24:22
 */
public class MybatisBuilder implements FileBuilder {

    /**
     * <mapper namespace="cn.chery.cmail.antispam.dao.AsHoneypotDao">
     *     <resultMap type="asHoneypot" id="FullResultMap">
     *         <id property="honeypotId" column="honeypot_id"/>
     *         <result property="emailAddr" column="email_addr"/>
     *     </resultMap>
     * </mapper>
     */
    @Override
    public String getContent(Mapping mapping) {
        MapperTag mapperTag = new MapperTag(String.format("%s.%sDao",
                mapping.getPackageName(), mapping.getClassName()));
        List<MappingItem> mappingItems = mapping.getMappingItems();
        mapperTag.setResultMapTag(getResultMap(mapping, mappingItems));
        mapperTag.setInsertTags(Collections.singletonList(getInsert(mapping, mappingItems)));
        String content = XMLUtil.toXML(mapperTag);
        content = "<?xml version=\"1.0\" encoding=\"" + mapping.getCharset().name() + "\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + content;
        return content;
    }

    private ResultMapTag getResultMap(Mapping mapping, List<MappingItem> mappingItems) {
        ResultMapTag resultMapTag = new ResultMapTag("FullResultMap",
                StringUtil.lowerFirstChar(mapping.getClassName()));
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

    @Override
    public String getFileName(String className) {
        return className + "Dao.xml";
    }

}
