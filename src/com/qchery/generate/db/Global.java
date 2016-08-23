package com.qchery.generate.db;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class Global {
    
    private static Properties props = new Properties();

    public static void initConfig() {
        try {
            props.load(Global.class.getClassLoader().getResourceAsStream("config/config.properties"));
        } catch (IOException e) {
            System.out.println(String.format("cmd=initConfig | result=FAIL | exception=%s", e));
        }
    }
    
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        String result = props.getProperty(key);
        if (StringUtils.isBlank(result)) {
            result = defaultValue;
        }
        return result;
    }
    
    public static boolean isUseJavaType() {
        return "true".equalsIgnoreCase(getProperty("java.type.enable"));
    }
    
}
