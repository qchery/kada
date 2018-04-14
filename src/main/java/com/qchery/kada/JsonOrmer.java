package com.qchery.kada;

import com.qchery.kada.builder.ClassInfoFileBuilder;
import com.qchery.kada.builder.java.JavaClassInfoFileBuilder;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.GenericClassInfo;
import com.qchery.kada.descriptor.java.IClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Chery
 * @date 2018/4/6 11:51
 */
public class JsonOrmer {

    private Logger logger = LoggerFactory.getLogger(JsonOrmer.class);

    private NameConvertor nameConvertor;

    private ClassInfoFileBuilder classInfoFileBuilder;

    public JsonOrmer() {
        this(new JavaClassInfoFileBuilder());
    }

    public JsonOrmer(ClassInfoFileBuilder classInfoFileBuilder) {
        this.nameConvertor = new DefaultNameConvertor();
        this.classInfoFileBuilder = classInfoFileBuilder;
    }

    public JsonOrmer(NameConvertor nameConvertor, ClassInfoFileBuilder classInfoFileBuilder) {
        this.nameConvertor = nameConvertor;
        this.classInfoFileBuilder = classInfoFileBuilder;
    }

    public void generateFile(String packageName, String className, String json) {
        IClassInfo classInfo = new JsonStructParser(nameConvertor).parse(packageName, className, json);
        generateFile(classInfo);
    }

    private void generateFile(IClassInfo classInfo) {
        // 生成泛型类包含的内部类对象
        if (classInfo instanceof GenericClassInfo) {
            GenericClassInfo genericClassInfo = (GenericClassInfo) classInfo;
            generateFile(genericClassInfo.getInnerClass());
        }

        // 为类的所有字段生成类文件
        if (!classInfo.isPrimitive()) {
            for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
                generateFile(fieldInfo.getClassInfo());
            }
        }

        if (!isClassExist(classInfo)) {
            logger.info("msg={} | javaType={}", "生成类文件", classInfo.getType());
            // 生成类文件
            try {
                FileCreator.createFile(classInfoFileBuilder.getFileInfo(classInfo));
            } catch (IOException e) {
                logger.error("msg={}", "文件生成失败", e);
            }
        }
    }

    private boolean isClassExist(IClassInfo classInfo) {
        boolean classExist = true;
        try {
            Class.forName(classInfo.getType());
        } catch (ClassNotFoundException e) {
            classExist = false;
        }
        return classExist;
    }

}
