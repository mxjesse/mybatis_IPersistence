package com.mx.pojo;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/24 3:52 下午
 */
public class MappedStatement {

    //mapper.xml id标识
    private String id;
    //返回值类型
    private String resultType;
    //参数类型
    private String parameterType;
    //sql语句
    private String sql;

    //mapper.xml标签 select|insert|update|delete
    private String actionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
