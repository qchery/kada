package com.qchery.kada.descriptor.java;

import java.util.List;

/**
 * @author Chery
 * @date 2018/4/6 10:46
 */
public interface IClassDescriptor {
    /**
     * 获取包名
     *
     * @return 包名
     */
    String getPackageName();

    /**
     * 获取类名
     *
     * @return 类名
     */
    String getClassName();

    /**
     * 获取字段描述列表
     *
     * @return 字段描述列表
     */
    List<FieldDescriptor> getFieldDescriptors();

    /**
     * 添加字段描述
     *
     * @param fieldDescriptor 字段描述
     */
    void addFieldDescriptor(FieldDescriptor fieldDescriptor);

    /**
     * 是否为基础类型
     *
     * @return 是否为基础类型
     */
    boolean isPrimitive();

    /**
     * 包含全路径的类型
     */
    default String getType() {
        return getPackageName() + "." + getClassName();
    }
}
