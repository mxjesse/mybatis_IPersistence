package com.mx.sqlSession;

import com.mx.config.BoundSql;
import com.mx.pojo.MappedStatement;
import com.mx.utils.GenericTokenParser;
import com.mx.utils.ParameterMapping;
import com.mx.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/24 6:31 下午
 */
public class SimpleExecutor implements Executor {

    private Connection connection = null;

    @Override
    public <E> List<E> query(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception {

        //1.获取连接
        connection = dataSource.getConnection();

        //2.获取执行的sql
        String sql = mappedStatement.getSql();
        //3.解析sql表达式为标准sql
        BoundSql boundSql = this.parseSql(sql);

        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        //4.参数类型
        String parameterType = mappedStatement.getParameterType();

        Class<?> clazz = null;
        if (parameterType != null) {
            clazz = Class.forName(parameterType);
        }

        //5.获取参数
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            Field field = clazz.getDeclaredField(content);
            field.setAccessible(true);

            Object o = field.get(params[0]);

            preparedStatement.setObject(i + 1, o);
        }

        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        String resultType = mappedStatement.getResultType();

        Class<?> rClazz = Class.forName(resultType);

        ArrayList<Object> objects = new ArrayList();

        while (resultSet.next()) {

            Object instance = rClazz.newInstance();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = metaData.getColumnCount();

            for (int i = 1; i <= count; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, rClazz);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(instance, value);
            }

            objects.add(instance);
        }

        return (List<E>) objects;
    }

    @Override
    public boolean addBatch(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception {

        //1.获取连接
        connection = dataSource.getConnection();

        //2.获取sql
        String sql = mappedStatement.getSql();

        //3.解析sql
        BoundSql boundSql = parseSql(sql);

        //4.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        //5.获取参数类型
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = Class.forName(parameterType);

        //6.获取参数
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        if (params[0] instanceof List) {
            for (Object e : (List) params[0]) {
                for (int i = 0; i < parameterMappingList.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappingList.get(i);
                    String content = parameterMapping.getContent();

                    Field field = clazz.getDeclaredField(content);
                    field.setAccessible(true);

                    Object o = field.get(e);
                    preparedStatement.setObject(i + 1, o);
                }
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }

        return true;
    }

    @Override
    public boolean update(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception {

        //1.获取连接
        connection = dataSource.getConnection();

        //2.获取sql
        String sql = mappedStatement.getSql();

        //3.解析sql
        BoundSql boundSql = parseSql(sql);

        //4.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        //5.获取参数类型
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = Class.forName(parameterType);

        //6.获取参数
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            Field field = clazz.getDeclaredField(content);
            field.setAccessible(true);

            Object o = field.get(params[0]);

            preparedStatement.setObject(i + 1, o);
        }

        //执行sql
        try {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(DataSource dataSource, MappedStatement mappedStatement, Object... params) throws Exception {

        //1.获取连接
        connection = dataSource.getConnection();

        //2.获取sql
        String sql = mappedStatement.getSql();

        //3.解析sql
        BoundSql boundSql = parseSql(sql);

        //4.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        //5.获取参数类型
        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = Class.forName(parameterType);

        //6.获取参数
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            Field field = clazz.getDeclaredField(content);
            field.setAccessible(true);

            Object o = field.get(params[0]);

            preparedStatement.setObject(i + 1, o);
        }

        //执行sql
        try {
            preparedStatement.execute();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * 解析sql
     *
     * @param sql
     * @return
     */
    public BoundSql parseSql(String sql) {

        ParameterMappingTokenHandler mappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", mappingTokenHandler);
        String s = tokenParser.parse(sql);

        List<ParameterMapping> parameterMappings = mappingTokenHandler.getParameterMappings();

        return new BoundSql(s, parameterMappings);
    }
}
