package com.qchery.kada.builder.java;

import com.qchery.kada.builder.ClassInfoFileBuilder;
import com.qchery.kada.descriptor.file.FileInfo;
import com.qchery.kada.descriptor.java.IClassInfo;

/**
 * java 源文件信息 Builder
 *
 * @author Chery
 * @date 2018/4/14 17:03
 */
public class JavaClassInfoFileBuilder implements ClassInfoFileBuilder {

    private JavaContentBuilder javaContentBuilder;

    public JavaClassInfoFileBuilder() {
        this.javaContentBuilder = new OriginalJavaContentBuilder();
    }

    public JavaClassInfoFileBuilder(JavaContentBuilder javaContentBuilder) {
        this.javaContentBuilder = javaContentBuilder;
    }

    @Override
    public FileInfo getFileInfo(IClassInfo classInfo) {
        String packagePath = classInfo.getPackageName().replaceAll("\\.", "/");
        String fileName = classInfo.getClassName() + ".java";
        String content = buildContent(classInfo);
        return new FileInfo(packagePath, fileName, content);
    }

    /**
     * 组装文件内容
     *
     * @param classInfo 类信息
     * @return 文件内容
     */
    public String buildContent(IClassInfo classInfo) {
        return javaContentBuilder.build(classInfo);
    }
}
