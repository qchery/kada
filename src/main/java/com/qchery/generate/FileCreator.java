package com.qchery.generate;

import com.qchery.generate.builder.FileBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件生成器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:48:57
 */
public class FileCreator {

    private static Logger logger = LoggerFactory.getLogger(FileCreator.class);

    public static void createFile(FileBuilder fileBuilder, ObjectDescriptor descriptor)
            throws IOException {

        String content = fileBuilder.getContent(descriptor);
        String packagePath = descriptor.getPackageName().replaceAll("\\.", "/");
        File file = newFile(fileBuilder.getFileName(descriptor.getClassName()), packagePath);
        try (FileOutputStream output = new FileOutputStream(file)) {
            IOUtils.write(content, output, descriptor.getCharset());
        }
    }

    private static File newFile(String fileName, String packagePath) {

        File destDir = new File(getFileStorageDir(packagePath));
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir.getAbsolutePath() + File.separatorChar + fileName);
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
        } catch (IOException e) {
            logger.error("msg={}", "文件创建失败!请检查文件路径!", e);
        }
        return destFile;
    }

    private static String getFileStorageDir(String packagePath) {
        String resDir = packagePath;

        String srcPath = System.getProperty("user.dir") + File.separatorChar
                + "src" + File.separatorChar;
        if (null == resDir) {
            return srcPath;
        }

        resDir = srcPath + resDir;
        return resDir.endsWith("/") ? resDir : resDir + File.separatorChar;
    }

}
