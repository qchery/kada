package com.qchery.kada.builder.java;

public interface AnnotationStrategy {
    /**
     * 声明注解
     *
     * @param annotationName 注解名称
     * @return 注解声明
     */
    String declareAnnotation(String annotationName);

    /**
     * 依赖类全量名
     *
     * @return 依赖类
     */
    String dependClass();
}