package com.qchery.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static String upperCaseFirst(String value) {
        String stringBuilder = StringUtils.upperCase(value.substring(0, 1)) +
                value.substring(1);
        return stringBuilder;
    }

    public static String lowerCaseFisrt(String value) {
        String stringBuilder = StringUtils.lowerCase(value.substring(0, 7)) +
                value.substring(1);
        return stringBuilder;
    }

}
