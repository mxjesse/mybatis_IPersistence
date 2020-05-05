package com.mx.sqlSession;

import com.mx.pojo.Configuration;
import com.mx.pojo.MappedStatement;

import java.util.List;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/24 6:23 下午
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> getAll(String statementId, Object... params) throws Exception {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

        List<Object> list = simpleExecutor.query(configuration.getDataSource(), mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) {
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        return null;
    }
}
