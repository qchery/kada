package com.qchery.kada.plugin;

import com.qchery.kada.DBOrmer;
import com.qchery.kada.builder.java.JavaMappingFileBuilder;
import com.qchery.kada.builder.java.OriginalJavaContentBuilder;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.db.ConnectParam;
import com.qchery.kada.db.DBHelper;
import com.qchery.kada.db.DBHelperFactory;
import com.qchery.kada.filter.TableNameFilter;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.nio.charset.Charset;

/**
 * @author Chery
 * @date 2018/7/1 14:29
 */
public class KadaTask extends DefaultTask {

    private ConnectParam connectParam = new ConnectParam();

    private TableNameFilter tableNameFilter;

    private NameConvertor nameConvertor;

    private String packageName;

    private Charset charset;

    private String rootPath;

    @TaskAction
    public void doTask() {
        DBHelperFactory dbHelperFactory = new DBHelperFactory();
        DBHelper dbHelper = dbHelperFactory.getDbHelper(connectParam);
        DBOrmer dbOrmer = DBOrmer.create().dbHelper(dbHelper)
                .fileBuilder(new JavaMappingFileBuilder(new OriginalJavaContentBuilder()))
                .packageName(packageName).tableNameFilter(tableNameFilter)
                .nameConvertor(nameConvertor).charset(charset).rootPath(rootPath).build();
        dbOrmer.generateFile();
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

    public ConnectParam getConnectParam() {
        return connectParam;
    }

    public void connect(Action<ConnectParam> action) {
        action.execute(connectParam);
    }

    @Input
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

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
