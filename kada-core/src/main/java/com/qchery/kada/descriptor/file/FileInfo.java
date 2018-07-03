package com.qchery.kada.descriptor.file;

import java.nio.charset.Charset;

/**
 * 文件信息
 *
 * @author Chery
 * @date 2018/4/6 12:17
 */
public class FileInfo {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private String packagePath;
    private String fileName;
    private String content;
    private Charset charset = DEFAULT_CHARSET;
    private String rootPath;

    public FileInfo() {
        this(System.getProperty("user.dir"));
    }

    public FileInfo(String rootPath) {
        this(rootPath, DEFAULT_CHARSET);
    }

    public FileInfo(String rootPath, Charset charset) {
        this.rootPath = rootPath;
        if (charset != null) {
            this.charset = charset;
        }
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
