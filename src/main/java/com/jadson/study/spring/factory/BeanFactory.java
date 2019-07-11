package com.jadson.study.spring.factory;

import com.jadson.study.spring.config.BeanDefinition;

public interface BeanFactory {

    Object getBean(String beanName);

    <T> T getBean(Class<T> clazz);

    void registBeanDefinition(BeanDefinition beanDefinition);
}
