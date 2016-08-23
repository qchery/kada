package com.qchery.generate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.qchery.generate.builder.FileBuilder;
import com.qchery.generate.convertor.DefaultNameConvertor;
import com.qchery.generate.convertor.NameConvertor;

/**
 * Java 对象生成 orm 配置
 * @author chinrui1016@163.com
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
     * @param clazz
     * @throws IOException 
     */
    public void generateFile(Class<?> clazz) throws IOException {
        String className = clazz.getSimpleName();
        ObjectDescriptor descriptor = new ObjectDescriptor();
        descriptor.setPackageName(clazz.getPackage().getName());
        descriptor.setClassName(className);
        descriptor.setTableName(convertor.toTableName(className));
        
        List<Item> items = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            Item item = new Item(field.getType().getName(), convertor.toColumnName(fieldName), fieldName);
            items.add(item);
        }
        descriptor.setItems(items);
        
        FileCreator.createFile(fileBuilder, descriptor);
    }
}
