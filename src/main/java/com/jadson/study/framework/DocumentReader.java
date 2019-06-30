package com.jadson.study.framework;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class DocumentReader {
    static Document read(InputStream inputStream) {
        Document document = null;

        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
}