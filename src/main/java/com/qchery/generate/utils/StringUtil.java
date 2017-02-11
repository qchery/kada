package com.qchery.generate.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    /**
     * 大驼峰命名转换
     *
     * @param value         原始名称
     * @param separatorChar 分隔符
     * @return 大驼峰命名
     */
    public static String upperCamelCase(String value, char separatorChar) {
        return convertCamelCase(value, separatorChar, true);
    }

    /**
     * 小驼峰命名转换
     *
     * @param value         原始名称
     * @param separatorChar 分隔符
     * @return 小驼峰命名
     */
    public static String lowerCamelCase(String value, char separatorChar) {
        return convertCamelCase(value, separatorChar, false);
    }

    /**
     * 格式化表名或属性名
     *
     * @param value         输入值
     * @param separatorChar 分隔符
     * @param isFirstUpper  确定首字母是否大写 true:大写 false:小写
     * @return 转换成驼峰命名的输出值
     */
    private static String convertCamelCase(String value, char separatorChar, boolean isFirstUpper) {
        StringBuilder builder = new StringBuilder();
        boolean upFlag = false;

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
            return StringUtil.upperFirstChar(builder.toString());
        } else {
            return builder.toString();
        }

    }

    public static String upperFirstChar(String value) {
        String stringBuilder = StringUtils.upperCase(value.substring(0, 1)) +
                value.substring(1);
        return stringBuilder;
    }

    public static String lowerFirstChar(String value) {
        String stringBuilder = StringUtils.lowerCase(value.substring(0, 1)) +
                value.substring(1);
        return stringBuilder;
    }

}
