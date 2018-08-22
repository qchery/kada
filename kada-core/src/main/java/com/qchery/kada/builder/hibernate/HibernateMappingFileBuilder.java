package com.qchery.kada.builder.hibernate;

import com.qchery.kada.builder.AbstractMappingFileBuilder;
import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.descriptor.java.IClassInfo;

/**
 * Hibernate配置文件建造器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午8:47:06
 */
public class HibernateMappingFileBuilder extends AbstractMappingFileBuilder {

    public HibernateMappingFileBuilder(ContentBuilder contentBuilder) {
        super(contentBuilder);
    }

    @Override
    public String getFileName(IClassInfo classInfo) {
        return classInfo.getClassName() + ".hbm.xml";
    }

}
