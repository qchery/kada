package com.qchery.kada;

import com.google.gson.*;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.descriptor.java.*;

import java.util.Map;

import static com.qchery.kada.descriptor.java.TypeDescriptor.PKG_JAVA_UTIL;

/**
 * @author Chery
 * @date 2018/4/4 20:40
 */
public class JsonStructParser {

    private NameConvertor nameConvertor;

    public JsonStructParser(NameConvertor nameConvertor) {
        this.nameConvertor = nameConvertor;
    }

    public IClassDescriptor parse(String packageName, String className, String json) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        IClassDescriptor classDescriptor = parseClassDescriptor(packageName, className, jsonElement);
        return classDescriptor;
    }

    private IClassDescriptor parseClassDescriptor(String packageName, String className, JsonElement rootElement) {
        IClassDescriptor classDescriptor = null;
        if (rootElement.isJsonPrimitive() || rootElement.isJsonNull()) {
            classDescriptor = ClassDescriptor.of(TypeDescriptor.STRING);
        } else if (rootElement.isJsonArray()) {
            classDescriptor = parseListClassDescriptor(packageName, className, rootElement);
        } else if (rootElement.isJsonObject()) {
            classDescriptor = ClassDescriptor.of(packageName, className);
            JsonObject jsonObject = rootElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                FieldDescriptor fieldDescriptor = parseFieldDescriptor(packageName, entry.getKey(), entry.getValue());
                if (fieldDescriptor.isNormal()) {
                    classDescriptor.addFieldDescriptor(fieldDescriptor);
                }
            }
        }
        return classDescriptor;
    }

    private FieldDescriptor parseFieldDescriptor(String packageName, String entryKey, JsonElement rootElement) {
        String fieldName = nameConvertor.toFieldName(entryKey);
        String className = nameConvertor.toClassName(entryKey);
        IClassDescriptor classDescriptor = parseClassDescriptor(packageName, className, rootElement);
        return new FieldDescriptor(classDescriptor, fieldName);
    }

    private GenericClassDescriptor parseListClassDescriptor(String packageName, String typeName, JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        IClassDescriptor innerClass = null;
        if (jsonArray.size() > 0) {
            JsonElement arrayElement = jsonArray.get(0);
            if (arrayElement != null) {
                innerClass = parseClassDescriptor(packageName, typeName, arrayElement);
            }
        }
        GenericClassDescriptor classDescriptor = new GenericClassDescriptor(new TypeDescriptor(PKG_JAVA_UTIL, "List"));
        classDescriptor.setInnerClass(innerClass);
        return classDescriptor;
    }

}
