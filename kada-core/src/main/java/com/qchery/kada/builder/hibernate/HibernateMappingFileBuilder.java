package com.qchery.kada.builder.hibernate;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.descriptor.Mapping;

/**
 * Hibernate配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午8:47:06
 */
public class HibernateMappingFileBuilder extends AbstractMappingFileBuilder {

    private HibernateContentBuilder hibernateContentBuilder;

    public HibernateMappingFileBuilder(HibernateContentBuilder hibernateContentBuilder) {
        this.hibernateContentBuilder = hibernateContentBuilder;
    }

    @Override
    public String getContent(Mapping mapping) {
        return hibernateContentBuilder.build(mapping);
    }

    @Override
    public String getFileName(String className) {
        return className + ".hbm.xml";
    }

}
