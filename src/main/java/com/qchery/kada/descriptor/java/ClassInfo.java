package com.qchery.kada.descriptor.java;

import java.util.*;

public class ClassInfo implements IClassInfo {

    /**
     * 类型声明
     */
    private TypeInfo typeInfo;
    /**
     * import 列表
     */
    private Set<String> importTypes;
    /**
     * 字段
     */
    private List<FieldInfo> fieldInfos;

    public static ClassInfo of(String packageName, String typeName) {
        return of(new TypeInfo(packageName, typeName));
    }

    public static ClassInfo of(TypeInfo typeInfo) {
        return new ClassInfo(typeInfo);
    }

    public static ClassInfo of(Class<?> clazz) {
        String packageName = clazz.getPackage().getName();
        String simpleName = clazz.getSimpleName();
        return of(new TypeInfo(packageName, simpleName));
    }

    protected ClassInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
        // 当类型不为基础类型时，分配字段列表
        if (!typeInfo.isPrimitive()) {
            fieldInfos = new ArrayList<>();
            importTypes = new HashSet<>();
        }
    }

    @Override
    public String getPackageName() {
        return typeInfo.getPackageName();
    }

    @Override
    public String getClassName() {
        return typeInfo.getTypeName();
    }

    @Override
    public List<FieldInfo> getFieldInfos() {
        if (typeInfo.isPrimitive()) {
            throw new RuntimeException("基础类型无字段描述");
        }
        return fieldInfos;
    }

    @Override
    public void addFieldInfo(FieldInfo fieldInfo) {
        if (typeInfo.isPrimitive()) {
            throw new RuntimeException("基础类型无字段描述");
        }
        this.fieldInfos.add(fieldInfo);
        if (!fieldInfo.isPrimitive()) {
            this.importTypes.add(fieldInfo.getType());
        }
    }

    @Override
    public boolean isPrimitive() {
        return typeInfo.isPrimitive();
    }

    @Override
    public String getType() {
        return typeInfo.getType();
    }

    @Override
    public Set<String> getImportTypes() {
        return this.importTypes;
    }

}
