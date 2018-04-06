package com.qchery.kada.descriptor.file;

import java.nio.charset.Charset;

/**
 * 文件信息
 *
 * @author Chery
 * @date 2018/4/6 12:17
 */
public class KadaFileDescriptor {

    private String packagePath;
    private String fileName;
    private String content;
    private Charset charset;

    public KadaFileDescriptor(String packagePath, String fileName, String content) {
        this.packagePath = packagePath;
        this.fileName = fileName;
        this.content = content;
    }

    public KadaFileDescriptor(String packagePath, String fileName, String content, Charset charset) {
        this(packagePath, fileName, content);
        this.charset = charset;
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
