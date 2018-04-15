package com.qchery.kada.builder.java;

import com.qchery.kada.descriptor.java.IClassInfo;
import com.qchery.kada.exception.ConfigException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * @author Chery
 * @date 2018/4/14 16:55
 */
public class TemplateJavaContentBuilder implements JavaContentBuilder {

    private Logger logger = LoggerFactory.getLogger(TemplateJavaContentBuilder.class);
    private Configuration cfg;

    public TemplateJavaContentBuilder() {
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_28);
            cfg.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/templates").getPath()));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
        } catch (Exception e) {
            throw new ConfigException("模板引擎配置信息初始化异常：" + e.getMessage());
        }
    }

    @Override
    public String build(IClassInfo classInfo) {
        String result = null;
        try (Writer out = new StringWriter()) {
            Template template = cfg.getTemplate("java_source_code.ftl");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("classInfo", classInfo);
            template.process(hashMap, out);
            result = out.toString();
        } catch (IOException | TemplateException e) {
            logger.error("msg=文件内容生成异常", e);
        }
        return result;
    }
}
