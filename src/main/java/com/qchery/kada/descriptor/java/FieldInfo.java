package com.qchery.kada.descriptor.java;

import java.lang.reflect.Field;

/**
 * 生成Item的关键字
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:38
 */
public class FieldInfo {

    /**
     * 类型描述
     */
    private IClassInfo classInfo;
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 注解名称
     */
    private String annotationName;

    public FieldInfo(Field field) {
        this(ClassInfo.of(field.getType()), field.getName());
    }

    public FieldInfo(IClassInfo classInfo, String fieldName) {
        this.classInfo = classInfo;
        this.fieldName = fieldName;
    }

    /**
     * 包含全路径的类型
     */
    public String getType() {
        return classInfo.getType();
    }

    public String getFieldName() {
        return fieldName;
    }

    /**
     * 不包含全路径的类型
     */
    public String getSimpleType() {
        return classInfo.getClassName();
    }

    public boolean isNormal() {
        return classInfo != null;
    }

    public IClassInfo getClassInfo() {
        return classInfo;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "classInfo=" + classInfo +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
