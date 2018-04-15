package com.qchery.kada.builder.java;

import com.qchery.kada.builder.TemplateHelper;
import com.qchery.kada.descriptor.java.IClassInfo;

import java.util.HashMap;

/**
 * @author Chery
 * @date 2018/4/14 16:55
 */
public class TemplateJavaContentBuilder extends TemplateHelper
        implements JavaContentBuilder {

    @Override
    public String build(IClassInfo classInfo) {
        HashMap<String, Object> parasMap = new HashMap<>();
        parasMap.put("classInfo", classInfo);
        return merge("java_source_code.ftl", parasMap);
    }

}
