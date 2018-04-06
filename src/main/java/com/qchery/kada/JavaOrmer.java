package com.qchery.kada;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.descriptor.db.ColumnDescriptor;
import com.qchery.kada.descriptor.db.TableDescriptor;
import com.qchery.kada.descriptor.file.KadaFileDescriptor;
import com.qchery.kada.descriptor.java.ClassDescriptor;
import com.qchery.kada.descriptor.java.FieldDescriptor;
import com.qchery.kada.descriptor.java.TypeDescriptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Java 对象生成 orm 配置
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:29:57
 */
public class JavaOrmer {

    private FileBuilder fileBuilder;
    private NameConvertor convertor = new DefaultNameConvertor();

    public JavaOrmer(FileBuilder fileBuilder) {
        this.fileBuilder = fileBuilder;
    }

    public void setConvertor(NameConvertor convertor) {
        this.convertor = convertor;
    }

    /**
     * 将POJO对象的字段转换成对应的XML格式
     *
     * @param clazz 类型字节码
     * @throws IOException 操作文件时，可能会出现异常
     */
    public void generateFile(Class<?> clazz) throws IOException {
        String className = clazz.getSimpleName();
        TypeDescriptor classTypeDescriptor = new TypeDescriptor(clazz.getPackage().getName(), className);
        ClassDescriptor classDescriptor = new ClassDescriptor(classTypeDescriptor);
        TableDescriptor tableDescriptor = new TableDescriptor(convertor.toTableName(className));

        List<MappingItem> mappingItems = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            TypeDescriptor typeDescriptor = new TypeDescriptor(fieldType.getPackage().getName(), fieldType.getSimpleName());
            FieldDescriptor fieldDescriptor = new FieldDescriptor(typeDescriptor, fieldName);

            ColumnDescriptor columnDescriptor = new ColumnDescriptor();
            columnDescriptor.setColumnName(convertor.toColumnName(fieldName));

            tableDescriptor.addColumnDescriptor(columnDescriptor);
            classDescriptor.addFieldDescriptor(fieldDescriptor);
            mappingItems.add(new MappingItem(fieldDescriptor, columnDescriptor));
        }

        Mapping mapping = new Mapping(classDescriptor, tableDescriptor);
        mapping.setMappingItems(mappingItems);
        mapping.setCharset(Charset.forName("utf-8"));

        FileCreator.createFile(getKadaFileDescriptor(mapping));
    }

    private KadaFileDescriptor getKadaFileDescriptor(Mapping mapping) {
        String content = fileBuilder.getContent(mapping);
        String fileName = fileBuilder.getFileName(mapping.getClassName());
        String packagePath = mapping.getPackageName().replaceAll("\\.", "/");
        Charset charset = mapping.getCharset();
        return new KadaFileDescriptor(packagePath, fileName, content, charset);
    }
}
