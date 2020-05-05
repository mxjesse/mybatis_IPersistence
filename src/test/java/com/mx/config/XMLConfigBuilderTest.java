package com.mx.config;

import com.mx.io.Resources;
import com.mx.pojo.Configuration;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class XMLConfigBuilderTest {

    @Test
    public void parseConfig() throws PropertyVetoException, DocumentException {

        InputStream inputStream = Resources.getSourceAsStream("sqlMapConfig.xml");
        Configuration configuration = new XMLConfigBuilder(new Configuration()).parseConfig(inputStream);

        System.out.println(configuration.getDataSource());
    }
}