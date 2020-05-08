package com.mx.sqlSession;

import com.mx.pojo.Configuration;
import com.mx.pojo.MappedStatement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface Executor {

    <E> List<E> query(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception;

    <E> boolean addBatch(DataSource dataSource, MappedStatement mappedStatement, List<E> list) throws Exception;

    void close() throws SQLException;
}
