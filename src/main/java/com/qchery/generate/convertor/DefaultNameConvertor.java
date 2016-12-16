package com.qchery.generate.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.qchery.common.utils.StringUtil;

/**
 * 默认命名格式
 * @author chinrui1016@163.com
 * @date 2016年5月15日 - 下午7:52:41
 */
public class DefaultNameConvertor implements NameConvertor {
    
    @Override
    public String toFieldName(String propName) {
        return toJava(propName, false);
    }

    @Override
    public String toClassName(String tableName) {
        return toJava(tableName, true);
    }
    
    /**
     * 格式化表名或属性名
     * @param name
     * @param isFirstUpper
     *      确定首字母是否大写 true:大写 false:小写
     * @return
     */
    private String toJava(String name, boolean isFirstUpper) {
        StringBuilder builder = new StringBuilder();
        boolean upFlag = false;
        
        for (char c : name.toLowerCase().toCharArray()) {
            
            if (upFlag) {
                builder.append((char) (c - 32));
                upFlag = false;
                continue;
            }
            
            if (c == '_') {
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
            sb.append("_");
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
}
