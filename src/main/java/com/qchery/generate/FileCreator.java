package com.qchery.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.qchery.generate.builder.FileBuilder;

/**
 * 文件生成器
 *
 * @author chinrui1016@163.com
 * @date 2016年5月15日 - 下午7:48:57
 */
public class FileCreator {

    public static void createFile(FileBuilder fileBuilder, ObjectDescriptor descriptor)
            throws IOException {

        String content = fileBuilder.getContent(descriptor);
        String packagePath = descriptor.getPackageName().replaceAll("\\.", "/");
        File file = newFile(fileBuilder.getFileName(descriptor.getClassName()), packagePath);
        try (FileOutputStream output = new FileOutputStream(file)) {
            IOUtils.write(content, output, Charset.defaultCharset());
        }
    }

    private static File newFile(String fileName, String packagePath) {

        File tarDir = new File(getFileStorageDir(packagePath));
        if (!tarDir.exists()) {
            tarDir.mkdirs();
        }

        File tarFile = new File(tarDir.getAbsolutePath() + File.separatorChar + fileName);
        try {
            if (!tarFile.exists()) {
                tarFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(String.format("文件创建失败!请检查文件路径!\n%s", e));
        }
        return tarFile;
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
