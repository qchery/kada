package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/8/22 23:37
 */
public class AuthorInfo {

    public static final String DEFAULT_AUTHOR = "Chery";

    private String author;

    private String company;

    public AuthorInfo() {
        this(DEFAULT_AUTHOR);
    }

    public AuthorInfo(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
