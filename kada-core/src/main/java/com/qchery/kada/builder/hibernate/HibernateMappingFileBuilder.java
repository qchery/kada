package com.qchery.kada.builder.hibernate;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.builder.hibernate.model.Clazz;
import com.qchery.kada.builder.hibernate.model.CompositeId;
import com.qchery.kada.builder.hibernate.model.HibernateMapping;
import com.qchery.kada.builder.hibernate.model.Property;
import com.qchery.kada.utils.XMLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Hibernate配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午8:47:06
 */
public class HibernateMappingFileBuilder extends AbstractMappingFileBuilder {

    @Override
    public String getContent(Mapping mapping) {
        HibernateMapping hibernateMapping = new HibernateMapping(mapping.getPackageName());
        Clazz clazz = new Clazz(mapping.getClassName(), mapping.getTableName());
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
            clazz.setId(createKeyProperty(pkMappingItems.get(0)));
        } else if (pkMappingItems.size() > 1) {
            CompositeId compositeId = new CompositeId();
            for (MappingItem pkMappingItem : pkMappingItems) {
                Property keyProperty = createKeyProperty(pkMappingItem);
                compositeId.addKeyProperty(keyProperty);
            }
            clazz.setCompositeId(compositeId);
        }

        // 处理非主键列
        for (MappingItem mappingItem : mappingItems) {
            Property property = new Property(mappingItem.getFieldName(), mappingItem.getJavaType());
            property.setLength(mappingItem.getLength());
            if (!mappingItem.getFieldName().equals(mappingItem.getColumnName())) {
                property.setColumn(mappingItem.getColumnName());
            }

            if (mappingItem.isNotNull() && !mappingItem.isPK()) {
                property.setNotNull(mappingItem.isNotNull());
            }

            clazz.addProperty(property);
        }
        hibernateMapping.setClazz(clazz);

        String content = XMLUtil.toXML(hibernateMapping);
        content = "<?xmlversion=\"1.0\"encoding='" + mapping.getCharset().name() + "'?>\n\n" +
                "<!DOCTYPE hibernate-hibernateMapping PUBLIC\n" +
                "     \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n" +
                "     \"http://hibernate.sourceforge.net/hibernate-hibernateMapping-3.0.dtd\">\n" + content;
        return content;
    }

    private Property createKeyProperty(MappingItem mappingItem) {
        Property id = new Property(mappingItem.getFieldName(), mappingItem.getJavaType());
        if (!mappingItem.getFieldName().equals(mappingItem.getColumnName())) {
            id.setColumn(mappingItem.getColumnName());
        }
        return id;
    }

    @Override
    public String getFileName(String className) {
        return className + ".hbm.xml";
    }

}
