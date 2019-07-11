package com.jadson.study.spring.factory;

import com.jadson.study.spring.config.BeanDefinition;
import com.jadson.study.spring.config.PropertyValue;
import com.jadson.study.spring.config.RuntimeBeanReference;
import com.jadson.study.spring.config.TypedStringValue;
import com.jadson.study.spring.convertor.IntegerTypedConvertor;
import com.jadson.study.spring.convertor.StringTypedConvertor;
import com.jadson.study.spring.convertor.TypedConvertor;
import com.jadson.study.spring.parse.XmlBeanDefinitionParser;
import com.jadson.study.spring.resource.ClassPathResourceLoader;
import com.jadson.study.spring.resource.Resource;
import com.jadson.study.spring.resource.ResourceLoader;
import com.jadson.study.util.ReflectUtil;
import com.jadson.study.util.Util;

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
        // 注册数据类型转换器
        registTypedConvertor();
        //将给定的文件转换为resource
        ResourceLoader resourceLoader = getResourceLoader(location);
        Resource resource = resourceLoader.load(location);
        // 创建读取配置文件的类
        XmlBeanDefinitionParser xmlBeanDefinitionReader = new XmlBeanDefinitionParser();
        // 读取配置文件，转化为BeanDefinition对象
        xmlBeanDefinitionReader.loadBeanDefinitions(this, resource);


    }

    /**
     * 注册资源加载器
     */
    private void registResourceLoader() {
        if (resourceLoaders == null) {
            resourceLoaders = new HashSet<>();
        }
        resourceLoaders.add(new ClassPathResourceLoader());
    }

    /**
     * 从已经注册的资源加载器中获取合适的加载器
     * @param location
     * @return
     */
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
        // 1.通过反射创建对象
        Object instance = ReflectUtil.createObject(beanDefinition.getBeanClassName(), null);

        // 2.设置属性
        setProperty(instance, beanDefinition);

        // 3. 初始化
        initBean(instance, beanDefinition);
        return instance;
    }

    /**
     * 初始化bean
     * @param instance
     * @param beanDefinition
     */
    private void initBean(Object instance, BeanDefinition beanDefinition) {
        if (Util.isNotEmpty(beanDefinition.getInitMethod())) {
            ReflectUtil.invokeMethod(instance, beanDefinition.getInitMethod());
        }
    }

    /**
     *  给bean设置属性
     * @param instance
     * @param beanDefinition
     */
    public void setProperty(Object instance, BeanDefinition beanDefinition) {
        // 获取属性列表，设置参数
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            Object value = propertyValue.getValue();
            if (value instanceof TypedStringValue) {
                // 如果是直接值，则通过反射方式赋值
                TypedStringValue typedStringValue = (TypedStringValue) value;
                Class<?> paramType = typedStringValue.getTargetType();
                String paramValue = typedStringValue.getValue();
                // 利用类型转换器将String类型的参数转换为需要的类型
                Object targetValue = getTypedConvertor(paramType).convert(paramValue);
                ReflectUtil.setProperty(instance, propertyValue.getName(), targetValue);
            } else {
                // 处理RuntimeBeanReference
                RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
                String ref = runtimeBeanReference.getRef();
                // 获取带引用的beanName，再从map中获取bean
                Object referenceBean = getBean(ref);
                ReflectUtil.setProperty(instance, propertyValue.getName(), referenceBean);
            }

        }
        singletonObjects.put(beanDefinition.getBeanName(), instance);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return null;
    }

    @Override
    public void registBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getBeanName(), beanDefinition);
    }

    /**
     * 注册参数类型转换器
     */
    private void registTypedConvertor() {
        if (typedConvertors == null) {
            typedConvertors = new HashSet<>();
        }
        typedConvertors.add(new IntegerTypedConvertor());
        typedConvertors.add(new StringTypedConvertor());
    }

    /**
     * 给bean设置属性时，需要将String类型的参数转换为需要的类型
     * 这里从注册好的类型转换器中选择合适的转换器
     * @param clazz
     * @return
     */
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
