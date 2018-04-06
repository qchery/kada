package com.qchery.kada;

import com.qchery.kada.descriptor.file.KadaFileDescriptor;
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

    public static void createFile(KadaFileDescriptor descriptor)
            throws IOException {

        File file = newFile(descriptor.getFileName(), descriptor.getPackagePath());
        try (FileOutputStream output = new FileOutputStream(file)) {
            IOUtils.write(descriptor.getContent(), output, descriptor.getCharset());
        }
    }

    private static File newFile(String fileName, String packagePath) throws IOException {

        File destDir = new File(getFileStorageDir(packagePath));
        if (!destDir.exists()) {
            boolean mkdirs = destDir.mkdirs();
            if (!mkdirs) {
                logger.warn("msg={} | path={}", "路径创建失败", destDir.getAbsolutePath());
            }
        }

        File destFile = new File(destDir.getAbsolutePath() + File.separatorChar + fileName);
        if (!destFile.exists()) {
            boolean flag = destFile.createNewFile();
            if (!flag) {
                logger.warn("msg={} | fileStorePath={}", "文件创建失败", destFile.getAbsolutePath());
            }
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
