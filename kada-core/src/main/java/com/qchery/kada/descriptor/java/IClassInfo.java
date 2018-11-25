package com.qchery.kada.descriptor.java;

import java.util.List;
import java.util.Set;

/**
 * @author Chery
 * @date 2018/4/6 10:46
 */
public interface IClassInfo {
    /**
     * 获取类注释
     *
     * @return 类注释
     */
    String getComment();

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
    List<FieldInfo> getFieldInfos();

    /**
     * 添加字段描述
     *
     * @param fieldInfo 字段描述
     */
    void addFieldInfo(FieldInfo fieldInfo);

    /**
     * 是否为基础类型
     *
     * @return 是否为基础类型
     */
    boolean isPrimitive();

    /**
     * 包含全路径的类型
     */
    String getType();

    /**
     * 获取需要导入的类型
     *
     * @return 需要导入的类型
     */
    Set<String> getImportTypes();

    /**
     * 转换成实体类的包名
     *
     * @return 实体类包名
     */
    String toEntityPackage();

    /**
     * 转换成Dao对应的包名
     *
     * @return Dao的包名
     */
    String toDaoPackage();

    /**
     * 转换成Dao对应的类名
     *
     * @return Dao的类名
     */
    String toDaoClassName();

    /**
     * 获取注释信息
     *
     * @return 注释信息
     */
    CommentInfo getCommentInfo();

    /**
     * 是否数数组类型
     */
    boolean isArray();
}
