package com.qchery.kada.builder.java;

public class JacksonAnnotationStrategy implements AnnotationStrategy {
    @Override
    public String declareAnnotation(String annotationName) {
        return "@JsonProperty(value = \"" + annotationName + "\")";
    }

    @Override
    public String dependClass() {
        return "com.fasterxml.jackson.annotation.JsonProperty;";
    }
}