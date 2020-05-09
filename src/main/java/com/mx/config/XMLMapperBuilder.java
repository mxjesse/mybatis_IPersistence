package com.mx.config;

import com.mx.enums.ActionTypeEnum;
import com.mx.pojo.Configuration;
import com.mx.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/24 5:54 下午
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parseMapper(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        for (ActionTypeEnum type : ActionTypeEnum.getAllTypes()) {
            String actionType = type.getActionType();

            List<Element> nodes = rootElement.selectNodes("//" + actionType);

            parseMapperNodes(namespace, actionType, nodes);
        }

//        List<Element> selectNodes = rootElement.selectNodes("//select");
//
//        for (Element select : selectNodes) {
//            String id = select.attributeValue("id");
//            String resultType = select.attributeValue("resultType");
//            String parameterType = select.attributeValue("parameterType");
//            String sql = select.getTextTrim();
//
//            MappedStatement mappedStatement = new MappedStatement();
//            mappedStatement.setId(id);
//            mappedStatement.setResultType(resultType);
//            mappedStatement.setParameterType(parameterType);
//            mappedStatement.setSql(sql);
//
//            String key = namespace + "." + id;
//            configuration.getMappedStatementMap().put(key, mappedStatement);
//        }
//
//        List<Element> insertNodes = rootElement.selectNodes("//insert");
//
//        for (Element insert : insertNodes) {
//            String id = insert.attributeValue("id");
//            String resultType = insert.attributeValue("resultType");
//            String parameterType = insert.attributeValue("parameterType");
//            String sql = insert.getTextTrim();
//
//            MappedStatement mappedStatement = new MappedStatement();
//            mappedStatement.setId(id);
//            mappedStatement.setResultType(resultType);
//            mappedStatement.setParameterType(parameterType);
//            mappedStatement.setSql(sql);
//
//            String key = namespace + "." + id;
//            configuration.getMappedStatementMap().put(key, mappedStatement);
//        }

        return configuration;
    }

    private void parseMapperNodes(String namespace, String actionType, List<Element> nodes) {

        for (Element insert : nodes) {
            String id = insert.attributeValue("id");
            String resultType = insert.attributeValue("resultType");
            String parameterType = insert.attributeValue("parameterType");
            String sql = insert.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            mappedStatement.setActionType(actionType);

            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }
    }
}
