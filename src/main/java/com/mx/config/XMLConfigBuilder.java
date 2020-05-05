package com.mx.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mx.io.Resources;
import com.mx.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/24 4:01 下午
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();

        List<Element> nodes = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        for (Element element : nodes) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.put(name, value);
        }

        //C3P0连接池
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(dataSource);

        //读取mapper配置
        List<Element> mapperList = rootElement.selectNodes("//mapper");

        for (Element mapper : mapperList) {
            String resource = mapper.attributeValue("resource");
            InputStream stream = Resources.getSourceAsStream(resource);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parseMapper(stream);
        }

        return configuration;
    }
}
