package com.mx.sqlSession;

import java.util.List;

public interface SqlSession {

    <E> List<E> getAll(String statementId, Object... params) throws Exception;

    <T> T selectOne(String statementId, Object... params);

    /**
     * 生成代理类
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> mapperClass);
}
