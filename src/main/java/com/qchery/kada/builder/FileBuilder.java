package com.qchery.kada.builder;

import java.sql.SQLException;

import com.qchery.kada.ObjectDescriptor;

/**
 * 文件建造器
 *
 * @author Chery
 * @date 2015年2月27日-下午4:17:17
 */
public interface FileBuilder {

    /**
     * 根据类名获取文件名
     *
     * @param className
     * @return {@link String}
     */
    String getFileName(String className);

    /**
     * 解析数据库关系为相应格式
     *
     * @param descriptor 对象描述
     * @return {@link String}
     * @throws SQLException
     */
    String getContent(ObjectDescriptor descriptor);

}
