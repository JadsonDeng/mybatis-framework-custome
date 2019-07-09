package com.jadson.study.spring;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractBeanFactory {

    // 保存从xml中解析出来的BeanDefinition
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    // 保存单例对象
    private Map<String, Object> singletonObjects = new HashMap<>();


    public DefaultListableBeanFactory(String location) {

        //将给定的文件转换为resource
        ResourceLoader resourceLoader = getResourceLoader(location);
        Resource resource = resourceLoader.load(location);
        // 创建读取配置文件的类
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
        // 读取配置文件，转化为BeanDefinition对象
        xmlBeanDefinitionReader.loadBeanDefinitions(this, resource);


    }

    private ResourceLoader getResourceLoader(String location) {
        if (location.startsWith("classpath:")) {
            return new ClassPathResourceLoader();
        }
        return null;
    }


    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return null;
    }
}
