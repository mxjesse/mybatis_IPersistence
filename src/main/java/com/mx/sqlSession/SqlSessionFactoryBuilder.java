package com.mx.sqlSession;

import com.mx.config.XMLConfigBuilder;
import com.mx.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/28 11:30 上午
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException {

        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(configuration);
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return sqlSessionFactory;
    }
}
