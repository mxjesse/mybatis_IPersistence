package com.mx.sqlSession;

import java.util.List;

public interface SqlSession {

//    <E> List<E> getAll(String statementId, Object... params) throws Exception;

    <T> T selectOne(String statementId, Object... params) throws Exception;

    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    boolean addList(String statementId, Object... params) throws Exception;

    /**
     * 生成代理类
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> mapperClass);
}
