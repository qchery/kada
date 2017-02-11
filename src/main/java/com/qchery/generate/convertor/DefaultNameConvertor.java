package com.qchery.generate.convertor;

import com.qchery.common.utils.StringUtil;
import com.qchery.generate.exception.ConfigException;
import org.apache.commons.lang3.StringUtils;

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
        return lowerCamelCase(propName);
    }

    @Override
    public String toClassName(String tableName) {
        return upperCamelCase(tableName);
    }

    protected String upperCamelCase(String value) {
        return convertCamelCase(value, true);
    }

    protected String lowerCamelCase(String value) {
        return convertCamelCase(value, false);
    }

    /**
     * 格式化表名或属性名
     *
     * @param value        输入值
     * @param isFirstUpper 确定首字母是否大写 true:大写 false:小写
     * @return 转换成驼峰命名的输出值
     */
    private String convertCamelCase(String value, boolean isFirstUpper) {
        StringBuilder builder = new StringBuilder();
        boolean upFlag = false;
        char separatorChar = getSeparator().charAt(0);

        for (char c : value.toLowerCase().toCharArray()) {

            if (upFlag) {
                builder.append((char) (c - 32));
                upFlag = false;
                continue;
            }

            if (c == separatorChar) {
                upFlag = true;
                continue;
            }

            builder.append(c);
        }

        if (isFirstUpper) {
            return StringUtil.upperCaseFirst(builder.toString());
        } else {
            return builder.toString();
        }

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
