package com.jadson.study.spring;

import org.dom4j.Element;

import java.util.List;

/**
 * 根据Spring的语义解析Document对象
 */
public class XmlBeanDefinitionDocumentParser {

    private BeanFactory beanFactory;

    public XmlBeanDefinitionDocumentParser(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Element rootElement) {
        // 获取所有子节点
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取节点名称
            String name = element.getName();
            if (name.equals("bean")) {
                // 解析bean标签
                parseDefaultElement(element);
            } else {
                // 解析自定义标签
                parseCustomElement(element);
            }
        }
    }

    private void parseCustomElement(Element element) {
    }

    /**
     * 解析bean标签
     * @param element
     */
    private void parseDefaultElement(Element element) {
        try {
            String id = element.attributeValue("id");
            String name = element.attributeValue("name");
            String clazzValue = element.attributeValue("class");
            Class<?> clazz = Class.forName(clazzValue);
            String initMethod = element.attributeValue("init-method");

            String beanName = id == null ? name : id;
            beanName = beanName == null ? clazz.getSimpleName() : beanName;

            // 创建BeanDefinition对象
            BeanDefinition beanDefinition = new BeanDefinition(beanName, clazzValue);
            beanDefinition.setInitMethod(initMethod);

            // 解析property标签
            List<Element> properties = element.elements("property");
            for (Element property : properties) {
                parsePropertyElement(property, beanDefinition);
            }

            // 注册BeanDefinition
            registBeanDefinition(beanDefinition);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void registBeanDefinition(BeanDefinition beanDefinition) {
    }

    private void parsePropertyElement(Element property, BeanDefinition beanDefinition) {
        String name = property.attributeValue("name");
        String value = property.attributeValue("value");
        beanDefinition.addPropertyValue(new PropertyValue(name, value));
    }
}
