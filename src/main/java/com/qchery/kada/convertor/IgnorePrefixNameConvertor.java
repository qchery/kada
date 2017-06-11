package com.qchery.kada.convertor;

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
public class IgnorePrefixNameConvertor extends DefaultNameConvertor {

    /**
     * 不需要进行截取的前缀集合
     */
    private Set<String> excludePrefixes = new HashSet<>();

    @Override
    public String toClassName(String realTableName) {
        int separatorIndex = realTableName.indexOf(getSeparator().charAt(0));
        String tablePrefix = realTableName.substring(0, separatorIndex);

        String tableName = null;
        for (String excludePrefix : excludePrefixes) {
            if (excludePrefix.equals(tablePrefix)) {
                tableName = realTableName;
                break;
            }
        }
        if (null == tableName) {
            tableName = realTableName.substring(separatorIndex + 1);
        }

        return super.toClassName(tableName);
    }

    public IgnorePrefixNameConvertor excludeSuffix(String suffix) {
        this.excludePrefixes.add(suffix);
        return this;
    }
}
