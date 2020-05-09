package com.mx.sqlSession;

import com.mx.enums.ActionTypeEnum;
import com.mx.pojo.Configuration;
import com.mx.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

//    @Override
//    public <E> List<E> getAll(String statementId, Object... params) throws Exception {
//
//        SimpleExecutor simpleExecutor = new SimpleExecutor();
//        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
//
//        List<Object> list = simpleExecutor.query(configuration.getDataSource(), mappedStatement, params);
//
//        return (List<E>) list;
//    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {

        List<Object> list = this.selectList(statementId, params);

        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        } else {
            throw new RuntimeException("结果不止一个!");
        }
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration.getDataSource(), mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public boolean addList(String statementId, Object... params) throws Exception {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

        boolean result = simpleExecutor.addBatch(configuration.getDataSource(), mappedStatement, params);
        return result;
    }

    @Override
    public boolean update(String statementId, Object... params) throws Exception {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

        boolean result = simpleExecutor.update(configuration.getDataSource(), mappedStatement, params);
        return result;
    }

    @Override
    public boolean delete(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

        boolean result = simpleExecutor.delete(configuration.getDataSource(), mappedStatement, params);
        return result;
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {

        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();

        Object proxyInstance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String methodName = method.getName();

                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + methodName;

                Type type = method.getGenericReturnType();

                MappedStatement mappedStatement = mappedStatementMap.get(statementId);

//                System.out.println("id :" + mappedStatement.getId());
                System.out.println("statementId: " + statementId +  ", actionType: " + mappedStatement.getActionType());

                //通过actionType判断返回值
                String actionType = mappedStatement.getActionType();
                if (ActionTypeEnum.SELECT.getActionType().equals(actionType)) {
                    List<Object> list = selectList(statementId, args);
                    return list;
                } else if (ActionTypeEnum.INSERT.getActionType().equals(actionType)) {
                    boolean b = addList(statementId, args);
                    return b;
                } else if (ActionTypeEnum.UPDATE.getActionType().equals(actionType)) {
                    boolean b = update(statementId, args);
                    return b;
                } else if (ActionTypeEnum.DELETE.getActionType().equals(actionType)) {
                    boolean b = delete(statementId, args);
                    return b;
                }

//                if (type instanceof ParameterizedType) {
//                    List<Object> list = selectList(statementId, args);
//                    return list;
//                } else if (type.getTypeName().equalsIgnoreCase("boolean") ) {
//                    boolean b = addList(statementId, args);
//                    return b;
//                }

                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }
}
