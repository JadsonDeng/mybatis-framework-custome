package com.jadson.study.mybatis.config;

import com.jadson.study.mybatis.framework.DocumentReader;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;

public class XMLMapperParser {

    private Configration configration;

    public XMLMapperParser(Configration configration) {
        this.configration = configration;
    }

    public void parse(InputStream inputStream) {
        Document document = new DocumentReader().read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> selects = rootElement.elements("select");
        for (Element element : selects) {
            parseStatement(element);
        }
    }

    private void parseStatement(Element element) {
        element.attributeValue("id");
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = getClassType(parameterType);

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = getClassType(resultType);

        element.attributeValue("statementType");
        String sqlText = element.getTextTrim();

        SqlSource sqlSource = new SqlSource(sqlText);
    }

    private Class<?> getClassType(String type) {
        if (type == null || "".equals(type)) {
            return null;
        }
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
