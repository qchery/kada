package com.qchery.kada.builder.java;


import com.qchery.kada.descriptor.java.CommentInfo;
import com.qchery.kada.descriptor.java.IClassInfo;
import com.qchery.kada.utils.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Chery
 * @date 2018/8/23 21:28
 */
public class CommentBuilder {

    public static String build(IClassInfo classInfo) {
        return build(classInfo, null);
    }

    public static String build(IClassInfo classInfo, String suffix) {
        StringBuilder builder = new StringBuilder();
        CommentInfo commentInfo = classInfo.getCommentInfo();
        if (commentInfo != null) {
            builder.append("/**\n");
            if (StringUtils.isNotBlank(commentInfo.getContent())) {
                builder.append(" * ").append(commentInfo.getContent())
                        .append(suffix != null ? suffix : "").append("\n");
                builder.append(" * \n");
            }
            if (StringUtils.isNotBlank(commentInfo.getAuthor())) {
                builder.append(" * @author ").append(commentInfo.getAuthor()).append("\n");
            }
            if (StringUtils.isNotBlank(commentInfo.getEmail())) {
                builder.append(" * @email ").append(commentInfo.getEmail()).append("\n");
            }
            if (StringUtils.isNotBlank(commentInfo.getCompany())) {
                builder.append(" * @company ").append(commentInfo.getCompany()).append("\n");
            }
            builder.append(" * @date ").append(DateTimeFormatter.ofPattern(commentInfo.getTimePattern()).format(LocalDateTime.now())).append("\n");
            builder.append(" */\n");
        }
        return builder.toString();
    }
}
