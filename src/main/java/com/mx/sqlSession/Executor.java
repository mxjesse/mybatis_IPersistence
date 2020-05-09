package com.mx.sqlSession;

import com.mx.pojo.Configuration;
import com.mx.pojo.MappedStatement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface Executor {

    <E> List<E> query(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception;

    boolean addBatch(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception;

    boolean update(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception;

    boolean delete(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception;

    void close() throws SQLException;
}
