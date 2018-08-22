package com.qchery.kada.descriptor.java;

/**
 * @author Chery
 * @date 2018/8/22 23:37
 */
public class AuthorInfo {

    public static final String DEFAULT_AUTHOR = "Chery";
    public static final String DEFAULT_EMAIL = "chinrui1016@163.com";

    private String author;

    private String email;

    private String company;

    public AuthorInfo() {
        this(DEFAULT_AUTHOR, DEFAULT_EMAIL);
    }

    public AuthorInfo(String author, String email) {
        this.author = author;
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
