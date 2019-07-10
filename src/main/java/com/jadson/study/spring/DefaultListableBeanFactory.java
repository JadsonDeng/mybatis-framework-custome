package com.jadson.study.spring;

import com.jadson.study.spring.convertor.IntegerTypedConvertor;
import com.jadson.study.spring.convertor.StringTypedConvertor;
import com.jadson.study.util.ReflectUtil;

import java.util.*;

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

    /**
     * 数据类型转换器
     */
    private Set<TypedConvertor> typedConvertors;


    public DefaultListableBeanFactory(String location) {
        // 注册资源加载器
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
        // 注册数据类型转换器
        registTypedConvertor();
        Object instance = singletonObjects.get(beanName);
        if (instance != null) {
            return instance;
        }

        return doGetBean(beanName);
    }


    private Object doGetBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            return null;
        }
        // 通过反射创建对象
        Object instance = ReflectUtil.createObject(beanDefinition.getBeanClassName(), null);

        // 获取属性列表，设置参数
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            Object value = propertyValue.getValue();
            String name = propertyValue.getName();
            if (value instanceof TypedStringValue) {
                // 如果是直接值，则通过反射方式赋值
                TypedStringValue typedStringValue = (TypedStringValue) value;
                Class<?> paramType = typedStringValue.getTargetType();
                String paramValue = typedStringValue.getValue();
                // 利用类型转换器将String类型的参数转换为需要的类型
                Object targetValue = getTypedConvertor(paramType).convert(paramValue);
                ReflectUtil.setProperty(instance, propertyValue.getName(), targetValue);
            } else {
                // TODO
            }

        }
        singletonObjects.put(beanName, instance);
        return instance;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return null;
    }

    @Override
    public void registBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getBeanName(), beanDefinition);
    }

    private void registTypedConvertor() {
        if (typedConvertors == null) {
            typedConvertors = new HashSet<>();
        }
        typedConvertors.add(new IntegerTypedConvertor());
        typedConvertors.add(new StringTypedConvertor());
    }

    private TypedConvertor getTypedConvertor(Class<?> clazz) {
        for (TypedConvertor typedConvertor : typedConvertors) {
            if (typedConvertor.isType(clazz)) {
                return typedConvertor;
            }
        }
        return null;
    }


    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public Map<String, Object> getSingletonObjects() {
        return singletonObjects;
    }

    public void setSingletonObjects(Map<String, Object> singletonObjects) {
        this.singletonObjects = singletonObjects;
    }


}
