package com.jadson.study.spring.parse;

import com.jadson.study.spring.factory.BeanFactory;
import com.jadson.study.spring.resource.Resource;
import com.jadson.study.util.DocumentReader;
import org.dom4j.Document;

public class XmlBeanDefinitionParser {

    /**
     * 解析配置文件，转换为BeanDefinition
     *
     * @param beanFactory
     * @param resource
     */
    public void loadBeanDefinitions(BeanFactory beanFactory, Resource resource) {
        // 转换为Document
        Document document = DocumentReader.read(resource.getInpusStream());
        // 委托给XmlBeanDefinitionDocumentReader去做解析
        XmlBeanDefinitionDocumentParser xmlBeanDefinitionDocumentReader = new XmlBeanDefinitionDocumentParser(beanFactory);
        xmlBeanDefinitionDocumentReader.loadBeanDefinitions(document.getRootElement());
    }
}
