package com.qchery.kada;

import com.qchery.kada.builder.FileBuilder;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.descriptor.Mapping;
import com.qchery.kada.descriptor.MappingItem;
import com.qchery.kada.descriptor.db.ColumnInfo;
import com.qchery.kada.descriptor.db.TableInfo;
import com.qchery.kada.descriptor.java.ClassInfo;
import com.qchery.kada.descriptor.java.FieldInfo;

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
        Mapping mapping = getMapping(clazz);
        FileCreator.createFile(fileBuilder.getFileInfo(mapping));
    }

    private Mapping getMapping(Class<?> clazz) {
        ClassInfo classInfo = ClassInfo.of(clazz);
        TableInfo tableInfo = new TableInfo(convertor.toTableName(clazz.getSimpleName()));

        List<MappingItem> mappingItems = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(convertor.toColumnName(field.getName()));
            tableInfo.addColumnInfo(columnInfo);

            FieldInfo fieldInfo = new FieldInfo(field);
            classInfo.addFieldInfo(fieldInfo);

            mappingItems.add(new MappingItem(fieldInfo, columnInfo));
        }

        Mapping mapping = new Mapping(classInfo, tableInfo);
        mapping.setMappingItems(mappingItems);
        mapping.setCharset(Charset.forName("utf-8"));
        return mapping;
    }

}
