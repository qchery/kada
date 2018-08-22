package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/8/22 23:23
 */
public class CommentInfo {

    private AuthorInfo authorInfo;

    private String content;

    private String timePattern = "yyyy/MM/dd HH:mm";

    public CommentInfo(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public CommentInfo(AuthorInfo authorInfo, String content) {
        this.authorInfo = authorInfo;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return authorInfo.getAuthor();
    }

    public String getEmail() {
        return authorInfo.getEmail();
    }

    public String getCompany() {
        return authorInfo.getCompany();
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }
}
