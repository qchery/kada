package com.qchery.kada;

import com.qchery.kada.builder.java.AnnotationStrategy;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.descriptor.file.FileInfo;
import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.GenericClassInfo;
import com.qchery.kada.descriptor.java.IClassInfo;
import com.qchery.kada.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Chery
 * @date 2018/4/6 11:51
 */
public class JsonOrmer {

    private Logger logger = LoggerFactory.getLogger(JsonOrmer.class);

    private NameConvertor nameConvertor;

    private AnnotationStrategy annotationStrategy;

    public JsonOrmer() {
        this.nameConvertor = new DefaultNameConvertor();
    }

    public JsonOrmer(NameConvertor nameConvertor) {
        this.nameConvertor = nameConvertor;
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
                FileCreator.createFile(getFileInfo(classInfo));
            } catch (IOException e) {
                logger.error("msg={}", "文件生成失败", e);
            }
        }
    }

    private FileInfo getFileInfo(IClassInfo classInfo) {
        String packagePath = classInfo.getPackageName().replaceAll("\\.", "/");
        String fileName = classInfo.getClassName() + ".java";
        String content = String.format("package %s;\n\n%s"
                        + "public class %s implements Serializable {\n%s}",
                classInfo.getPackageName(), declareImports(classInfo),
                classInfo.getClassName(), getMainContent(classInfo));
        return new FileInfo(packagePath, fileName, content);
    }

    private String getMainContent(IClassInfo classInfo) {
        StringBuilder fields = declareFileds(classInfo);
        StringBuilder setGetMethods = declareSetGetMethods(classInfo);
        StringBuilder toStringMethod = declareToString(classInfo);
        return fields.append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(IClassInfo classInfo) {
        StringBuilder methods = new StringBuilder();
        for (FieldInfo descriptor : classInfo.getFieldInfos()) {
            String javaType = getGenericSimpleType(descriptor);
            String fieldName = descriptor.getFieldName();
            String fcuFieldName = StringUtil.upperFirstChar(fieldName);
            methods.append(formatGetMethod(javaType, fieldName, fcuFieldName));
            methods.append(formatSetMethod(javaType, fieldName, fcuFieldName));
        }
        return methods;
    }

    private String formatSetMethod(String javaType, String fieldName,
                                   String fcuFieldName) {
        String setMethod = String.format("public void set%s(%s %s) {\n"
                + "    this.%s = %s;\n"
                + "}\n", fcuFieldName, javaType, fieldName, fieldName, fieldName);
        return setMethod;
    }

    private String formatGetMethod(String javaType, String fieldName,
                                   String fcuFieldName) {
        String getMethod = String.format("public %s get%s() {\n"
                + "    return this.%s;\n"
                + "}\n", javaType, fcuFieldName, fieldName);
        return getMethod;
    }

    private StringBuilder declareToString(IClassInfo classInfo) {
        StringBuilder toStringMethod = new StringBuilder("@Override\npublic String toString() {\n" +
                "return \"").append(classInfo.getClassName()).append(" [\"");
        List<FieldInfo> fieldInfos = classInfo.getFieldInfos();
        for (int i = 0; i < fieldInfos.size(); i++) {
            FieldInfo descriptor = fieldInfos.get(i);
            toStringMethod.append("+").append("\"")
                    .append(descriptor.getFieldName()).append(" = \"+")
                    .append(descriptor.getFieldName()).append("+ \"");
            if (i == fieldInfos.size() - 1) {
                toStringMethod.append("]\";");
            } else {
                toStringMethod.append(",\"\n");
            }
        }
        toStringMethod.append("}");
        return toStringMethod;
    }

    private StringBuilder declareFileds(IClassInfo classInfo) {
        StringBuilder fields = new StringBuilder();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            if (annotationStrategy != null) {
                fields.append(annotationStrategy.declareAnnotation(fieldInfo.getAnnotationName()));
            }
            fields.append("private ").append(getGenericSimpleType(fieldInfo))
                    .append(" ").append(fieldInfo.getFieldName()).append(";\n");
        }
        return fields;
    }

    private String getGenericSimpleType(FieldInfo descriptor) {
        String simpleType = descriptor.getSimpleType();
        // 拼接泛型类型
        IClassInfo fieldClassInfo = descriptor.getClassInfo();
        if (fieldClassInfo instanceof GenericClassInfo) {
            String innerClassName = ((GenericClassInfo) fieldClassInfo).getInnerClass().getClassName();
            simpleType = simpleType + "<" + innerClassName + ">";
        }
        return simpleType;
    }

    private String declareImports(IClassInfo classInfo) {
        Set<String> importSet = new HashSet<>();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            if (!fieldInfo.isPrimitive()) {
                importSet.add(fieldInfo.getType());
            }
        }
        importSet.add("java.io.Serializable");
        if (annotationStrategy != null) {
            importSet.add(annotationStrategy.dependClass());
        }

        StringBuilder imports = new StringBuilder();
        for (String importType : importSet) {
            imports.append("import ").append(importType).append(";\n");
        }

        return imports.toString();
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

    public void setAnnotationStrategy(AnnotationStrategy annotationStrategy) {
        this.annotationStrategy = annotationStrategy;
    }

}
