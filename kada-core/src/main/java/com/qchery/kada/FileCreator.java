package com.qchery.kada;

import com.qchery.kada.descriptor.file.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 文件生成器
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:48:57
 */
public class FileCreator {

    private static Logger logger = LoggerFactory.getLogger(FileCreator.class);

    public static void createFile(String rootPath, FileInfo fileInfo)
            throws IOException {

        File file = newFile(rootPath, fileInfo);
        try (FileOutputStream output = new FileOutputStream(file)) {
            Charset charset = fileInfo.getCharset();
            if (charset == null) {
                charset = Charset.defaultCharset();
            }
            output.write(fileInfo.getContent().getBytes(charset));
        }
    }

    private static File newFile(String rootPath, FileInfo fileInfo) throws IOException {

        File destDir = new File(getFileStorageDir(rootPath, fileInfo.getPackagePath()));
        if (!destDir.exists()) {
            boolean mkdirs = destDir.mkdirs();
            if (!mkdirs) {
                logger.warn("msg={} | path={}", "路径创建失败", destDir.getAbsolutePath());
            }
        }

        File destFile = new File(destDir.getAbsolutePath() + File.separatorChar + fileInfo.getFileName());
        logger.debug("msg={} | fileStorePath={}", "创建文件", destFile.getAbsolutePath());

        if (!destFile.exists()) {
            boolean flag = destFile.createNewFile();
            if (!flag) {
                logger.warn("msg={} | fileStorePath={}", "文件创建失败", destFile.getAbsolutePath());
            }
        }
        return destFile;
    }

    private static String getFileStorageDir(String rootPath, String packagePath) {
        String resourceDir = packagePath;

        rootPath = appendSeparator(rootPath);
        if (null == resourceDir) {
            return rootPath;
        }

        resourceDir = rootPath + resourceDir;
        return appendSeparator(resourceDir);
    }

    private static String appendSeparator(String resDir) {
        return resDir.endsWith(File.separator) ? resDir : resDir + File.separatorChar;
    }

}
