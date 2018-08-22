package com.qchery.kada.descriptor.java;

import java.util.*;

public class ClassInfo implements IClassInfo {

    /**
     * 是否为实体类
     */
    private boolean entityClass;
    /**
     * 类注释
     */
    private String comment;
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

    public static ClassInfo ofEntity(String packageName, String typeName) {
        ClassInfo classInfo = of(new TypeInfo(packageName, typeName));
        classInfo.setEntityClass(true);
        return classInfo;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public String getPackageName() {
        return typeInfo.getPackageName();
    }

    @Override
    public String getClassName() {
        return typeInfo.getTypeName();
    }

    public boolean isEntityClass() {
        return entityClass;
    }

    public void setEntityClass(boolean entityClass) {
        this.entityClass = entityClass;
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
        return (isEntityClass() ? toEntityPackage() : getPackageName()) + "." + typeInfo.getTypeName();
    }

    @Override
    public Set<String> getImportTypes() {
        return this.importTypes;
    }

    @Override
    public String toEntityPackage() {
        return getPackageName() + ".entity";
    }

    @Override
    public String toDaoPackage() {
        return getPackageName() + ".dao";
    }

    @Override
    public String toDaoClassName() {
        return getClassName() + "Dao";
    }

}
