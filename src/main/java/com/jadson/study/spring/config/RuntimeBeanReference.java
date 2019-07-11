package com.jadson.study.spring.config;

public class RuntimeBeanReference {

    private String ref;


    public RuntimeBeanReference(String ref) {
        this.ref = ref;
    }


    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "RuntimeBeanReference{" +
                "ref='" + ref + '\'' +
                '}';
    }
}
