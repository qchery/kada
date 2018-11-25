package com.qchery.kada.plugin;

import com.qchery.kada.DBOrmer;
import com.qchery.kada.builder.java.JavaMappingFileBuilder;
import com.qchery.kada.builder.java.OriginalCommonJavaContentBuilder;
import com.qchery.kada.builder.java.OriginalJavaContentBuilder;
import com.qchery.kada.builder.java.OriginalLombokJavaContentBuilder;
import com.qchery.kada.builder.mybatis.MybatisDaoMappingFileBuilder;
import com.qchery.kada.builder.mybatis.MybatisMappingFileBuilder;
import com.qchery.kada.builder.mybatis.OriginalMybatisContentBuilder;
import com.qchery.kada.builder.mybatis.OriginalMybatisDaoContentBuilder;
import com.qchery.kada.db.DBHelper;
import com.qchery.kada.db.DBHelperFactory;
import com.qchery.kada.descriptor.file.FileInfo;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * @author Chery
 * @date 2018/7/1 14:29
 */
public class KadaTask extends DefaultTask {

    @TaskAction
    public void doTask() {
        KadaExtension kadaExtension = getProject().getExtensions().getByType(KadaExtension.class);

        String rootPath = getRootPath(kadaExtension.getTarget());

        System.out.println("Generate Started : Target Dir : " + rootPath);

        try {
            DBHelperFactory dbHelperFactory = new DBHelperFactory();
            DBHelper dbHelper = dbHelperFactory.getDbHelper(kadaExtension.getConnectParam());
            DBOrmer.DBOrmerBuilder builder = DBOrmer.create().dbHelper(dbHelper)
                    .authorInfo(kadaExtension.getAuthorInfo())
                    .packageName(kadaExtension.getPackageName())
                    .tableNameFilter(kadaExtension.getTableNameFilter())
                    .nameConvertor(kadaExtension.getNameConvertor())
                    .fileInfo(new FileInfo(rootPath + "/src/main/java", kadaExtension.getCharset()));

            OriginalJavaContentBuilder javaContentBuilder = getOriginalJavaContentBuilder(kadaExtension.isUseLombok());
            builder.addFileBuilder(new JavaMappingFileBuilder(javaContentBuilder))
                    .addFileBuilder(new MybatisDaoMappingFileBuilder(new OriginalMybatisDaoContentBuilder()))
                    .build().generateFile();

            builder.clearBuilders()
                    .fileInfo(new FileInfo(rootPath + "/src/main/resources", kadaExtension.getCharset()))
                    .addFileBuilder(new MybatisMappingFileBuilder(new OriginalMybatisContentBuilder()))
                    .build().generateFile();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        System.out.println("Generate Ended : Target Dir : " + rootPath);
    }

    public String getRootPath(File target) {
        String rootPath;
        if (target != null) {
            rootPath = target.getAbsolutePath();
        } else {
            rootPath = getProject().getProjectDir().getAbsolutePath();
        }
        return rootPath;
    }

    public OriginalJavaContentBuilder getOriginalJavaContentBuilder(boolean useLombok) {
        OriginalJavaContentBuilder javaContentBuilder;
        if (useLombok) {
            javaContentBuilder = new OriginalLombokJavaContentBuilder();
        } else {
            javaContentBuilder = new OriginalCommonJavaContentBuilder();
        }
        return javaContentBuilder;
    }

}
