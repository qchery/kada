package com.qchery.kada.builder.hibernate;

import com.qchery.kada.descriptor.Mapping;

/**
 * @author Chery
 * @date 2018/6/30 19:11
 */
public interface HibernateContentBuilder {

    String build(Mapping mapping);

}
