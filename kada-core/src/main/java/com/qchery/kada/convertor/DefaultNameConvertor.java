package com.qchery.kada.convertor;

import com.qchery.kada.exception.ConfigException;
import com.qchery.kada.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认命名格式
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:52:41
 */
public class DefaultNameConvertor implements NameConvertor {

    private static final String DEFAULT_SEPARATOR = "_";
    /**
     * 表名分隔符
     */
    private String separator;

    @Override
    public String toFieldName(String propName) {
        char separatorChar = getSeparator().charAt(0);
        return StringUtils.lowerCamelCase(propName, separatorChar);
    }

    @Override
    public String toClassName(String tableName) {
        char separatorChar = getSeparator().charAt(0);
        return StringUtils.upperCamelCase(tableName, separatorChar);
    }

    // --------------------- Java 转 Database Started ---------------------------

    @Override
    public String toTableName(String className) {
        return toDatabase(className);
    }

    @Override
    public String toColumnName(String fieldName) {
        return toDatabase(fieldName);
    }

    private String toDatabase(String name) {
        String result = name;
        List<String> words = splitWithUpCase(name);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            sb.append(getSeparator());
        }
        if (sb.length() != 0) {
            result = sb.substring(0, sb.length() - 1);
        }

        return StringUtils.lowerCase(result);
    }

    private List<String> splitWithUpCase(String value) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean isLastCharUp = false;
        for (char c : value.toCharArray()) {
            if (c >= 65 && c <= 90 && builder.length() > 0) {
                if (!isLastCharUp) {
                    result.add(builder.toString());
                    builder.delete(0, builder.length());
                    isLastCharUp = true;
                }
            }
            builder.append(c);
        }
        result.add(builder.toString());
        return result;
    }

    // --------------------- Java 转 Database End ---------------------------

    public String getSeparator() {
        if (StringUtils.isNotBlank(separator)) {
            return separator;
        }
        return DEFAULT_SEPARATOR;
    }

    public void setSeparator(String separator) {
        if (StringUtils.isBlank(separator)) {
            throw new ConfigException("分隔符不能为空");
        }
        separator = separator.trim();
        if (separator.length() > 1) {
            throw new ConfigException("分隔符只能为一个字符");
        }
        this.separator = separator;
    }
}
