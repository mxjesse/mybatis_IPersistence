package com.mx.config;

import com.mx.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/26 11:26 上午
 */
public class BoundSql {

    public BoundSql(String sql, List<ParameterMapping> parameterMappingList) {
        this.sql = sql;
        this.parameterMappingList = parameterMappingList;
    }

    /**
     * 解析后的sql
     */
    private String sql;

    private List<ParameterMapping> parameterMappingList = new ArrayList<ParameterMapping>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
