package com.qchery.kada.plugin;

import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.db.ConnectParam;
import com.qchery.kada.descriptor.java.AuthorInfo;
import com.qchery.kada.filter.TableNameFilter;
import org.gradle.api.Action;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @author Chery
 * @date 2018/7/2 22:42
 */
public class KadaExtension {
    private ConnectParam connectParam = new ConnectParam();

    private TableNameFilter tableNameFilter;

    private NameConvertor nameConvertor;

    private String packageName;

    private Charset charset;

    private File target;

    private boolean useLombok;

    private AuthorInfo authorInfo = new AuthorInfo();

    void connect(Action<ConnectParam> action) {
        action.execute(connectParam);
    }

    public ConnectParam getConnectParam() {
        return connectParam;
    }

    void authorInfo(Action<AuthorInfo> action) {
        action.execute(authorInfo);
    }

    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public TableNameFilter getTableNameFilter() {
        return tableNameFilter;
    }

    public void setTableNameFilter(TableNameFilter tableNameFilter) {
        this.tableNameFilter = tableNameFilter;
    }

    public NameConvertor getNameConvertor() {
        return nameConvertor;
    }

    public void setNameConvertor(NameConvertor nameConvertor) {
        this.nameConvertor = nameConvertor;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    public boolean isUseLombok() {
        return useLombok;
    }

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }
}
