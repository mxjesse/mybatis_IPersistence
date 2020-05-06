package com.mx.sqlSession;

import com.mx.pojo.Configuration;
import com.mx.pojo.MappedStatement;

import java.lang.reflect.*;
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

        Object instance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String methodName = method.getName();

                String className = method.getDeclaringClass().getName();

                String key = className + "." + methodName;

                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(key);

                Type type = method.getGenericReturnType();

                if (type instanceof ParameterizedType) {

                }

                return null;
            }
        });
        return null;
    }
}
