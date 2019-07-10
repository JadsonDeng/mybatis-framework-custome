package com.jadson.study.spring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultListableBeanFactory extends AbstractBeanFactory {

    /**
     * 保存从xml中解析出来的BeanDefinition
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    /**
     * 保存单例对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 注册资源的加载器
     */
    private Set<ResourceLoader> resourceLoaders;


    public DefaultListableBeanFactory(String location) {
        registResourceLoader();
        //将给定的文件转换为resource
        ResourceLoader resourceLoader = getResourceLoader(location);
        Resource resource = resourceLoader.load(location);
        // 创建读取配置文件的类
        XmlBeanDefinitionParser xmlBeanDefinitionReader = new XmlBeanDefinitionParser();
        // 读取配置文件，转化为BeanDefinition对象
        xmlBeanDefinitionReader.loadBeanDefinitions(this, resource);


    }

    private void registResourceLoader() {
        if (resourceLoaders == null) {
            resourceLoaders = new HashSet<>();
        }
        resourceLoaders.add(new ClassPathResourceLoader());
    }

    private ResourceLoader getResourceLoader(String location) {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.canRead(location)) {
                return resourceLoader;
            }
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

    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public void addBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName, beanDefinition);
    }

    public Map<String, Object> getSingletonObjects() {
        return singletonObjects;
    }

    public void setSingletonObjects(Map<String, Object> singletonObjects) {
        this.singletonObjects = singletonObjects;
    }


}
