package com.jadson.study.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Element;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigParser {

//    <configration>
//    <environments default="dev">
//        <environment id="dev">
//            <dataSource type="DBCP">
//                <property name="driver" value="com.mysql.jdbc.Driver"/>
//                <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis_custom"/>
//                <property name="username" value="root"/>
//                <property name="password" value=""/>
//            </dataSource>
//        </environment>
//    </environments>
//
//    <mappers>
//        <mapper resource="UserMapper.xml"/>
//    </mappers>
//</configration>

    private Configration configration;

    public XMLConfigParser() {
        configration = new Configration();
    }

    public Configration parse(Element rootElement) {
        parseEnvironments(rootElement.element("environments"));
        parseMappers(rootElement.element("mappers"));
        return configration;
    }

    /**
     * 解析mappers标签
     *
     * @param mappers
     */
    private void parseMappers(Element mappers) {
        List<Element> mapperList = mappers.elements("mapper");
        if (mapperList != null && mapperList.size() > 0) {
            for (Element element : mapperList) {
                String resource = element.attributeValue("resource");
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
                new XMLMapperParser(configration).parse(inputStream);
            }
        }
    }

    /**
     * 解析environments标签
     *
     * @param environments
     */
    private void parseEnvironments(Element environments) {

        // 获取默认的环境
        String defaulEnvironment = environments.attributeValue("default");
        // 获取配置的环境列表
        List<Element> environmentList = environments.elements("environment");

        // 遍历配置的所有environment
        for (Element environment : environmentList) {
            // 获取environment的id：<environment id="dev">
            String environmentId = environment.attributeValue("id");
            if (environmentId != null && environmentId.equals(defaulEnvironment)) {
                parseDataSource(environment.element("dataSource"));
            }
        }

    }

    private void parseDataSource(Element dataSourceElement) {
        String dataSourceType = dataSourceElement.attributeValue("type");
        if (dataSourceType == null || "".equals(dataSourceType)) {
            dataSourceType = "DBCP";
        }
        List<Element> elements = dataSourceElement.elements("property");

        Properties properties = new Properties();
        for (Element element : elements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        if ("DBCP".equals(dataSourceType)) {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configration.setDataSource(dataSource);
        }

    }
}
