package com.jadson.study.framework;

import com.jadson.study.config.Configration;
import com.jadson.study.config.XMLConfigParser;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

public class SqlSessionFactoryBuilder {

    private Configration configration;

    public SqlSessionFactory build(InputStream inputStream) {
        // TODO 将inputStream转换为Configration
        Document document = DocumentReader.read(inputStream);
        configration = new XMLConfigParser().parse(document.getRootElement());
        return build();

    }

    public SqlSessionFactory build(Reader reader) {
        // TODO

        return build();
    }


    public SqlSessionFactory build() {
        return new DefaultSqlSessionFactory(configration);
    }
}
