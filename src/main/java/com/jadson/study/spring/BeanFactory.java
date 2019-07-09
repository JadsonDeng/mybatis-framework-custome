package com.jadson.study.spring;

public interface BeanFactory {

    Object getBean(String beanName);

    <T> T getBean(Class<T> clazz);
}
