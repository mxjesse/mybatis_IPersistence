package com.mx.sqlSession;

import com.mx.pojo.Configuration;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/28 2:01 下午
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
