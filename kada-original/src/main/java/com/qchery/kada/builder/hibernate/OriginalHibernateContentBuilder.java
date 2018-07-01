package com.qchery.kada.builder.hibernate;

import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.utils.XMLUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chery
 * @date 2018/6/30 19:13
 */
public class OriginalHibernateContentBuilder implements HibernateContentBuilder {

    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String COLUMN = "column";
    public static final String ID = "id";
    public static final String PACKAGE = "package";
    public static final String TABLE = "table";
    public static final String CLASS = "class";
    public static final String COMPOSITE_ID = "composite-id";
    public static final String KEY_PROPERTY = "key-property";
    public static final String LENGTH = "length";
    public static final String PROPERTY = "property";

    @Override
    public String build(Mapping mapping) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(mapping.getCharset().name());
        document.addDocType("hibernate-hibernateMapping",
                "-//Hibernate/Hibernate Mapping DTD 3.0//EN",
                "http://hibernate.sourceforge.net/hibernate-hibernateMapping-3.0.dtd");

        Element mappingElement = document.addElement("hibernate-mapping")
                .addAttribute(PACKAGE, mapping.getPackageName());

        Element classElement = mappingElement.addElement(CLASS)
                .addAttribute(NAME, mapping.getClassName())
                .addAttribute(TABLE, mapping.getTableName());

        List<MappingItem> mappingItems = mapping.getMappingItems();
        // 提取出主键列
        List<MappingItem> pkMappingItems = new ArrayList<>();
        Iterator<MappingItem> iterator = mappingItems.iterator();
        while (iterator.hasNext()) {
            MappingItem mappingItem = iterator.next();
            if (mappingItem.isPK()) {
                pkMappingItems.add(mappingItem);
                iterator.remove();
            }
        }

        // 处理主键列，如果单一主键则使用 id，如果是复合主键则使用 composite-id
        if (pkMappingItems.size() == 1) {
            MappingItem mappingItem = pkMappingItems.get(0);
            Element idEle = classElement.addElement(ID)
                    .addAttribute(NAME, mappingItem.getFieldName())
                    .addAttribute(TYPE, mappingItem.getJavaType());
            if (!mappingItem.getFieldName().equals(mappingItem.getColumnName())) {
                idEle.addAttribute(COLUMN, mappingItem.getColumnName());
            }
        } else if (pkMappingItems.size() > 1) {
            Element compositeEle = classElement.addElement(COMPOSITE_ID);
            for (MappingItem pkMappingItem : pkMappingItems) {
                Element keyPropEle = compositeEle.addElement(KEY_PROPERTY)
                        .addAttribute(NAME, pkMappingItem.getFieldName())
                        .addAttribute(TYPE, pkMappingItem.getJavaType());
                if (!pkMappingItem.getFieldName().equals(pkMappingItem.getColumnName())) {
                    keyPropEle.addAttribute(COLUMN, pkMappingItem.getColumnName());
                }
            }
        }

        // 处理非主键列
        for (MappingItem mappingItem : mappingItems) {
            Element element = classElement.addElement(PROPERTY)
                    .addAttribute(NAME, mappingItem.getFieldName())
                    .addAttribute(TYPE, mappingItem.getJavaType());

            if (!mappingItem.getFieldName().equals(mappingItem.getColumnName())) {
                element.addAttribute(COLUMN, mappingItem.getColumnName());
            }

            element.addAttribute(LENGTH, String.valueOf(mappingItem.getLength()));

            if (mappingItem.isNotNull() && !mappingItem.isPK()) {
                element.addAttribute("not-null", String.valueOf(mappingItem.isNotNull()));
            }
        }

        return XMLUtils.toXML(document);
    }

}
