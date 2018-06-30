package com.qchery.kada.builder.java;

public class FastjsonAnnotationStrategy implements AnnotationStrategy {
    @Override
    public String declareAnnotation(String annotationName) {
        return "@JSONField(name = \"" + annotationName + "\")";
    }

    @Override
    public String dependClass() {
        return "com.alibaba.fastjson.annotation.JSONField;";
    }
}