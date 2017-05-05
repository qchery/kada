package com.qchery.kada;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;

/**
 * Java 对象生成 orm 配置
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:29:57
 */
public class JavaOrmer {

    private FileBuilder fileBuilder = null;
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
     * @param clazz
     * @throws IOException
     */
    public void generateFile(Class<?> clazz) throws IOException {
        String className = clazz.getSimpleName();
        ClassDescriptor classDescriptor = new ClassDescriptor();
        classDescriptor.setPackageName(clazz.getPackage().getName());
        classDescriptor.setClassName(className);
        classDescriptor.setCharset(Charset.forName("utf-8"));
        TableDescriptor tableDescriptor = new TableDescriptor(convertor.toTableName(className));

        List<MappingItem> mappingItems = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            FieldDescriptor fieldDescriptor = new FieldDescriptor(field.getType().getName(), fieldName);

            ColumnDescriptor columnDescriptor = new ColumnDescriptor();
            columnDescriptor.setColumnName(convertor.toColumnName(fieldName));

            tableDescriptor.addColumnDescriptor(columnDescriptor);
            classDescriptor.addFieldDescriptor(fieldDescriptor);
            mappingItems.add(new MappingItem(fieldDescriptor, columnDescriptor));
        }

        Mapping mapping = new Mapping(classDescriptor, tableDescriptor);
        mapping.setMappingItems(mappingItems);
        FileCreator.createFile(fileBuilder, mapping);
    }
}
