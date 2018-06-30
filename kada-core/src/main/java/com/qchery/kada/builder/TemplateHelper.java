package com.qchery.kada.builder;

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
 * @date 2018/4/15 14:20
 */
public class TemplateHelper {

    private Logger logger = LoggerFactory.getLogger(TemplateHelper.class);
    private Configuration cfg;

    public TemplateHelper() {
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

    protected String merge(String name, HashMap<String, Object> parasMap) {
        String result = null;
        try (Writer out = new StringWriter()) {
            Template template = cfg.getTemplate(name);
            template.process(parasMap, out);
            result = out.toString();
        } catch (IOException | TemplateException e) {
            logger.error("msg=文件内容生成异常", e);
        }
        return result;
    }

}
