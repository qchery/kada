package com.qchery.generate.hibernate.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("hibernate-mapping")
public class HibernateMapping {

    @XStreamAlias("package")
    @XStreamAsAttribute
    private String pkg;
    
    @XStreamAlias("class")
    private Clazz clazz;

    public HibernateMapping(String pkg) {
        super();
        this.pkg = pkg;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
    
}
