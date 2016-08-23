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
        StringBuffer sb = new StringBuffer();
        boolean upFlag = false;
        
        for (char c : name.toLowerCase().toCharArray()) {
            
            if (upFlag) {
                sb.append((char) (c - 32));
                upFlag = false;
                continue;
            }
            
            if (c == '_') {
                upFlag = true;
                continue;
            }
            
            sb.append(c);
        }
        
        if (isFirstUpper) {
            return StringUtil.upperCaseFirst(sb.toString());
        } else {
            return sb.toString();
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
        StringBuffer sb = new StringBuffer();
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
        StringBuffer buf = new StringBuffer();
        boolean isLastCharUp = false;
        for (char c : value.toCharArray()) {
            if (c >= 65 && c <= 90 && buf.length() > 0) {
                if (!isLastCharUp) {
                    result.add(buf.toString());
                    buf.delete(0, buf.length());
                    isLastCharUp = true;
                }
            }
            buf.append(c);
        }
        result.add(buf.toString());
        return result;
    }

    // --------------------- Java 转 Database End ---------------------------
}
