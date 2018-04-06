package com.qchery.kada;

import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.descriptor.file.KadaFileDescriptor;
import com.qchery.kada.descriptor.java.FieldDescriptor;
import com.qchery.kada.descriptor.java.GenericClassDescriptor;
import com.qchery.kada.descriptor.java.IClassDescriptor;
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

    public void generateFile(String packageName, String className, String json) {
        DefaultNameConvertor nameConvertor = new DefaultNameConvertor();
        IClassDescriptor classDescriptor = new JsonStructParser(nameConvertor).parse(packageName, className, json);
        generateFile(classDescriptor);
    }

    private void generateFile(IClassDescriptor classDescriptor) {
        // 生成泛型类包含的内部类对象
        if (classDescriptor instanceof GenericClassDescriptor) {
            GenericClassDescriptor genericClassDescriptor = (GenericClassDescriptor) classDescriptor;
            generateFile(genericClassDescriptor.getInnerClass());
        }

        // 为类的所有字段生成类文件
        if (!classDescriptor.isPrimitive()) {
            for (FieldDescriptor fieldDescriptor : classDescriptor.getFieldDescriptors()) {
                generateFile(fieldDescriptor.getClassDescriptor());
            }
        }

        if (!isClassExist(classDescriptor)) {
            logger.info("msg={} | javaType={}", "生成类文件", classDescriptor.getType());
            // 生成类文件
            try {
                FileCreator.createFile(kadaFileDescriptor(classDescriptor));
            } catch (IOException e) {
                logger.error("msg={}", "文件生成失败", e);
            }
        }
    }

    private KadaFileDescriptor kadaFileDescriptor(IClassDescriptor classDescriptor) {
        String packagePath = classDescriptor.getPackageName().replaceAll("\\.", "/");
        String fileName = classDescriptor.getClassName() + ".java";
        String content = String.format("package %s;\n\n%s"
                        + "public class %s implements Serializable {\n%s}",
                classDescriptor.getPackageName(), declareImports(classDescriptor),
                classDescriptor.getClassName(), getMainContent(classDescriptor));
        return new KadaFileDescriptor(packagePath, fileName, content);
    }

    private String getMainContent(IClassDescriptor classDescriptor) {
        StringBuilder fields = declareFileds(classDescriptor);
        StringBuilder setGetMethods = declareSetGetMethods(classDescriptor);
        StringBuilder toStringMethod = declareToString(classDescriptor);
        return fields.append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(IClassDescriptor classDescriptor) {
        StringBuilder methods = new StringBuilder();
        for (FieldDescriptor descriptor : classDescriptor.getFieldDescriptors()) {
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

    private StringBuilder declareToString(IClassDescriptor classDescriptor) {
        StringBuilder toStringMethod = new StringBuilder("@Override\npublic String toString() {\n" +
                "return \"").append(classDescriptor.getClassName()).append(" [\"");
        List<FieldDescriptor> fieldDescriptors = classDescriptor.getFieldDescriptors();
        for (int i = 0; i < fieldDescriptors.size(); i++) {
            FieldDescriptor descriptor = fieldDescriptors.get(i);
            toStringMethod.append("+").append("\"")
                    .append(descriptor.getFieldName()).append(" = \"+")
                    .append(descriptor.getFieldName()).append("+ \"");
            if (i == fieldDescriptors.size() - 1) {
                toStringMethod.append("]\";");
            } else {
                toStringMethod.append(",\"\n");
            }
        }
        toStringMethod.append("}");
        return toStringMethod;
    }

    private StringBuilder declareFileds(IClassDescriptor classDescriptor) {
        StringBuilder fields = new StringBuilder();
        for (FieldDescriptor descriptor : classDescriptor.getFieldDescriptors()) {
            fields.append("private ").append(getGenericSimpleType(descriptor))
                    .append(" ").append(descriptor.getFieldName()).append(";\n");
        }
        return fields;
    }

    private String getGenericSimpleType(FieldDescriptor descriptor) {
        String simpleType = descriptor.getSimpleType();
        // 拼接泛型类型
        IClassDescriptor fieldClassDescriptor = descriptor.getClassDescriptor();
        if (fieldClassDescriptor instanceof GenericClassDescriptor) {
            String innerClassName = ((GenericClassDescriptor) fieldClassDescriptor).getInnerClass().getClassName();
            simpleType = simpleType + "<" + innerClassName + ">";
        }
        return simpleType;
    }

    private String declareImports(IClassDescriptor classDescriptor) {
        Set<String> importSet = new HashSet<>();
        for (FieldDescriptor fieldDescriptor : classDescriptor.getFieldDescriptors()) {
            importSet.add(fieldDescriptor.getType());
        }
        importSet.add("java.io.Serializable");

        StringBuilder imports = new StringBuilder();
        for (String importType : importSet) {
            imports.append("import ").append(importType).append(";\n");
        }

        return imports.toString();
    }

    private boolean isClassExist(IClassDescriptor classDescriptor) {
        boolean classExist = true;
        try {
            Class.forName(classDescriptor.getType());
        } catch (ClassNotFoundException e) {
            classExist = false;
        }
        return classExist;
    }

}
