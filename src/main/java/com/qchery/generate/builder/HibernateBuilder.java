package com.qchery.generate.builder;

import com.qchery.generate.Item;
import com.qchery.generate.ObjectDescriptor;
import com.qchery.generate.model.hibernate.*;
import com.qchery.generate.utils.XMLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Hibernate配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午8:47:06
 */
public class HibernateBuilder implements FileBuilder {

    @Override
    public String getContent(ObjectDescriptor descriptor) {
        HibernateMapping mapping = new HibernateMapping(descriptor.getPackageName());
        Clazz clazz = new Clazz(descriptor.getClassName(), descriptor.getTableName());
        List<Item> items = descriptor.getItems();

        // 提取出主键列
        List<Item> pkItems = new ArrayList<>();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.isPK()) {
                pkItems.add(item);
                iterator.remove();
            }
        }

        // 处理主键列，如果单一主键则使用 id，如果是复合主键则使用 composite-id
        if (pkItems.size() == 1) {
            clazz.setId(createKeyProperty(pkItems.get(0)));
        } else if (pkItems.size() > 1) {
            CompositeId compositeId = new CompositeId();
            for (Item pkItem : pkItems) {
                Property keyProperty = createKeyProperty(pkItem);
                compositeId.addKeyProperty(keyProperty);
            }
            clazz.setCompositeId(compositeId);
        }

        // 处理非主键列
        for (Item item : items) {
            Property property = new Property(item.getFieldName(), item.getType());
            property.setLength(item.getLength());
            if (!item.getFieldName().equals(item.getColumnName())) {
                property.setColumn(item.getColumnName());
            }

            if (item.isNotNull() && !item.isPK()) {
                property.setNotNull(item.isNotNull());
            }

            clazz.addProperty(property);
        }
        mapping.setClazz(clazz);

        String content = XMLUtil.toXML(mapping);
        content = "<?xmlversion=\"1.0\"encoding='" + descriptor.getCharset().name() + "'?>\n\n" +
                "<!DOCTYPE hibernate-mapping PUBLIC\n" +
                "     \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n" +
                "     \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n" + content;
        return content;
    }

    private Property createKeyProperty(Item item) {
        Property id = new Property(item.getFieldName(), item.getType());
        if (!item.getFieldName().equals(item.getColumnName())) {
            id.setColumn(item.getColumnName());
        }
        return id;
    }

    @Override
    public String getFileName(String className) {
        return className + ".hbm.xml";
    }

}
