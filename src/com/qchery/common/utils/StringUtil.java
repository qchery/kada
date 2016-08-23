package com.qchery.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static String upperCaseFirst(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringUtils.upperCase(value.substring(0, 1)));
        stringBuilder.append(value.substring(1));
        return stringBuilder.toString();
    }

    public static String lowerCaseFisrt(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringUtils.lowerCase(value.substring(0, 7)));
        stringBuilder.append(value.substring(1));
        return stringBuilder.toString();
    }

}
