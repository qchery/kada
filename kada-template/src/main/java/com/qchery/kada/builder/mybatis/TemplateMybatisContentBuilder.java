package com.qchery.kada.builder.mybatis;

import com.qchery.kada.builder.ContentBuilder;
import com.qchery.kada.builder.TemplateHelper;
import com.qchery.kada.descriptor.Mapping;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * @author Chery
 * @date 2018/4/15 11:27
 */
public class TemplateMybatisContentBuilder extends TemplateHelper
        implements ContentBuilder {

    @Override
    public String build(Charset charset, Mapping mapping) {
        HashMap<String, Object> parasMap = new HashMap<>();
        parasMap.put("charset", charset);
        parasMap.put("mapping", mapping);
        return merge("mybatis.ftlx", parasMap);
    }

}
