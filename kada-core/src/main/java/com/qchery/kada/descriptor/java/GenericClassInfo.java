package com.qchery.kada.descriptor.java;

/**
 * 泛型类型描述
 *
 * @author Chery
 * @date 2018/4/5 21:36
 */
public class GenericClassInfo extends ClassInfo {

    private IClassInfo innerClass;

    public GenericClassInfo(TypeInfo typeInfo) {
        super(typeInfo);
    }

    public void setInnerClass(IClassInfo innerClass) {
        this.innerClass = innerClass;
    }

    public IClassInfo getInnerClass() {
        return innerClass;
    }
}
