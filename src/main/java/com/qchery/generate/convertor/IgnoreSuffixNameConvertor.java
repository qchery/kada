package com.qchery.generate.convertor;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 忽略前缀命名格式
 * 若表名中带有 '_' 则只取第一个 '_' 后的内容进行命名生成，
 * 忽略前缀仅作用于表名，对字段名无效
 * eg:
 * 1. 表名为 sys_user 则只取 user 用于类名生成
 * 2. 表名为 sys_role_menu 则只取 role_menu 用于生成
 *
 * @author Chery
 * @date 2017年2月11日 - 上午10:52:41
 */
public class IgnoreSuffixNameConvertor extends DefaultNameConvertor {

    /**
     * 前缀
     */
    private String suffix;
    /**
     * 不需要进行截取的前缀集合
     */
    private Set<String> excludeSuffixes = new HashSet<>();

    @Override
    public String toClassName(String tableName) {
        int separatorIndex = tableName.indexOf(getSeparator().charAt(0));
        String suffix = tableName.substring(0, separatorIndex);

        String realTableName = null;
        for (String excludeSuffix : excludeSuffixes) {
            if (excludeSuffix.equals(suffix)) {
                realTableName = tableName;
            }
        }
        if (null == realTableName) {
            realTableName = tableName.substring(separatorIndex + 1);
        }

        return super.toClassName(realTableName);
    }

    @Override
    public String toTableName(String className) {
        String tableName = super.toTableName(className);
        if (StringUtils.isNotBlank(suffix)) {
            tableName = suffix + getSeparator() + tableName;
        }
        return tableName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public IgnoreSuffixNameConvertor excludeSuffix(String suffix) {
        this.excludeSuffixes.add(suffix);
        return this;
    }
}
