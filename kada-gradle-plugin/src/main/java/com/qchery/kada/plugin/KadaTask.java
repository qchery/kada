package com.qchery.kada.plugin;

import com.qchery.kada.DBOrmer;
import com.qchery.kada.builder.java.JavaMappingFileBuilder;
import com.qchery.kada.builder.java.OriginalJavaContentBuilder;
import com.qchery.kada.db.DBHelper;
import com.qchery.kada.db.DBHelperFactory;
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

        File target = kadaExtension.getTarget();
        String rootPath = getProject().getProjectDir().getAbsolutePath() + "/src/main/java";

        if (target != null) {
            rootPath = target.getAbsolutePath();
        }

        System.out.println("Generate Started : Target Dir : " + rootPath);

        DBHelperFactory dbHelperFactory = new DBHelperFactory();
        DBHelper dbHelper = dbHelperFactory.getDbHelper(kadaExtension.getConnectParam());
        DBOrmer dbOrmer = DBOrmer.create().dbHelper(dbHelper)
                .fileBuilder(new JavaMappingFileBuilder(new OriginalJavaContentBuilder()))
                .packageName(kadaExtension.getPackageName())
                .tableNameFilter(kadaExtension.getTableNameFilter())
                .nameConvertor(kadaExtension.getNameConvertor())
                .charset(kadaExtension.getCharset())
                .rootPath(rootPath).build();
        dbOrmer.generateFile();

        System.out.println("Generate Ended : Target Dir : " + rootPath);
    }

}
