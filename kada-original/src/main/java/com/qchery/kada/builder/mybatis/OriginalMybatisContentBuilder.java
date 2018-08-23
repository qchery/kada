package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.utils.XMLUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import static com.qchery.kada.descriptor.Space.ofFour;
import static com.qchery.kada.descriptor.Space.ofSix;

/**
 * @author Chery
 * @date 2018/4/15 11:11
 */
public class OriginalMybatisContentBuilder implements ContentBuilder {

    public static final String FULL_RESULT_MAP = "FullResultMap";

    @Override
    public String build(Charset charset, Mapping mapping) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(charset.name());
        document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN",
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd");

        Element mapperEle = document.addElement("mapper")
                .addAttribute("namespace", mapping.toDaoPackage() + "." +
                        mapping.toDaoClassName());

        List<MappingItem> mappingItems = mapping.getMappingItems();

        // 字段映射生成
        Element resultMapEle = mapperEle.addElement("resultMap")
                .addAttribute("id", FULL_RESULT_MAP)
                .addAttribute("type", mapping.getType());
        for (MappingItem item : mappingItems) {
            Element propEle;
            if (item.isPK()) {
                propEle = resultMapEle.addElement("id");
            } else {
                propEle = resultMapEle.addElement("result");
            }
            propEle.addAttribute("property", item.getFieldName())
                    .addAttribute("column", item.getColumnName());
        }

        // select 方法生成
        addSelect(mapperEle, mapping);
        // insert 方法生成
        addInsert(mapperEle, mapping);
        // update 方法生成
        addUpdate(mapperEle, mapping);

        return XMLUtils.toXML(document);
    }

    private void addSelect(Element element, Mapping mapping) {
        Element select = element.addElement("select")
                .addAttribute("id", "getById")
                .addAttribute("resultMap", FULL_RESULT_MAP);

        List<MappingItem> mappingItems = mapping.getMappingItems();
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append(ofFour()).append("SELECT \n");
        Iterator<MappingItem> iterator = mappingItems.iterator();
        while (iterator.hasNext()) {
            MappingItem item = iterator.next();
            builder.append(ofSix()).append(item.getColumnName());
            if (iterator.hasNext()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append(ofFour()).append("FROM ").append(mapping.getTableName()).append(" ");

        select.addText(builder.toString());
        addWhere(select, mapping);
    }

    private void addWhere(Element element, Mapping mapping) {
        List<MappingItem> pkItems = mapping.getPkItems();
        Element where = element.addElement("where");
        if (pkItems.size() == 1) {
            MappingItem pkItem = pkItems.get(0);
            where.addText(pkItem.getColumnName() + " = #{" + pkItem.getFieldName() + "}");
        } else {
            for (MappingItem pkItem : pkItems) {
                where.addElement("if").addAttribute("test", "null != " + pkItem.getFieldName())
                        .addText("AND " + pkItem.getColumnName() + " = #{" + pkItem.getFieldName() + "}");
            }
        }
    }

    private void addInsert(Element element, Mapping mapping) {
        StringBuilder builder = new StringBuilder()
                .append("\n").append(ofFour()).append("INSERT INTO ")
                .append(mapping.getTableName())
                .append(" (\n");
        List<MappingItem> items = mapping.getMappingItems();
        Iterator<MappingItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            MappingItem item = iterator.next();
            builder.append(ofSix()).append(item.getColumnName());
            if (iterator.hasNext()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append(ofFour()).append(") VALUES (\n");
        Iterator<MappingItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            MappingItem item = itemIterator.next();
            builder.append(ofSix()).append("#{").append(item.getFieldName()).append("}");
            if (itemIterator.hasNext()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append(ofFour()).append(")\n  ");

        element.addElement("insert").addAttribute("id", "insert").addText(builder.toString());
    }

    private void addUpdate(Element element, Mapping mapping) {
        Element update = element.addElement("update").addAttribute("id", "updateSelective");
        update.addText("\n" + ofFour() + "UPDATE " + mapping.getTableName());
        Element set = update.addElement("set");
        List<MappingItem> items = mapping.getMappingItems();
        Iterator<MappingItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            MappingItem item = iterator.next();
            if (!item.isPK()) {
                Element ifEle = set.addElement("if").addAttribute("test", "null != " + item.getFieldName())
                        .addText(item.getColumnName() + " = #{" + item.getFieldName() + "}");
                if (iterator.hasNext()) {
                    ifEle.addText(",");
                }
            }
        }
        addWhere(update, mapping);
    }

}
