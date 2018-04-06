package com.qchery.kada.descriptor.java;

/**
 * 泛型类型描述
 *
 * @author Chery
 * @date 2018/4/5 21:36
 */
public class GenericClassDescriptor extends ClassDescriptor {

    private IClassDescriptor innerClass;

    public GenericClassDescriptor(TypeDescriptor typeDescriptor) {
        super(typeDescriptor);
    }

    public void setInnerClass(IClassDescriptor innerClass) {
        this.innerClass = innerClass;
    }

    public IClassDescriptor getInnerClass() {
        return innerClass;
    }
}
