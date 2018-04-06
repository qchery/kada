package com.qchery.kada;

import com.google.gson.*;
import com.qchery.kada.convertor.NameConvertor;
import com.qchery.kada.descriptor.java.*;

import java.util.Map;

import static com.qchery.kada.descriptor.java.TypeInfo.PKG_JAVA_UTIL;

/**
 * @author Chery
 * @date 2018/4/4 20:40
 */
public class JsonStructParser {

    private NameConvertor nameConvertor;

    public JsonStructParser(NameConvertor nameConvertor) {
        this.nameConvertor = nameConvertor;
    }

    public IClassInfo parse(String packageName, String className, String json) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        IClassInfo classInfo = parseClassInfo(packageName, className, jsonElement);
        return classInfo;
    }

    private IClassInfo parseClassInfo(String packageName, String className, JsonElement rootElement) {
        IClassInfo classInfo = null;
        if (rootElement.isJsonPrimitive() || rootElement.isJsonNull()) {
            classInfo = ClassInfo.of(TypeInfo.STRING);
        } else if (rootElement.isJsonArray()) {
            classInfo = parseListClassInfo(packageName, className, rootElement);
        } else if (rootElement.isJsonObject()) {
            classInfo = ClassInfo.of(packageName, className);
            JsonObject jsonObject = rootElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                FieldInfo fieldInfo = parseFieldInfo(packageName, entry.getKey(), entry.getValue());
                if (fieldInfo.isNormal()) {
                    classInfo.addFieldInfo(fieldInfo);
                }
            }
        }
        return classInfo;
    }

    private FieldInfo parseFieldInfo(String packageName, String entryKey, JsonElement rootElement) {
        String fieldName = nameConvertor.toFieldName(entryKey);
        String className = nameConvertor.toClassName(entryKey);
        IClassInfo classInfo = parseClassInfo(packageName, className, rootElement);
        return new FieldInfo(classInfo, fieldName);
    }

    private GenericClassInfo parseListClassInfo(String packageName, String typeName, JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        IClassInfo innerClass = null;
        if (jsonArray.size() > 0) {
            JsonElement arrayElement = jsonArray.get(0);
            if (arrayElement != null) {
                innerClass = parseClassInfo(packageName, typeName, arrayElement);
            }
        }
        GenericClassInfo classInfo = new GenericClassInfo(new TypeInfo(PKG_JAVA_UTIL, "List"));
        classInfo.setInnerClass(innerClass);
        return classInfo;
    }

}
