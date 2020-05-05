package com.mx.io;

import java.io.InputStream;

/**
 * @author mx
 * @version 1.0
 * @description
 * @date 2020/4/24 3:57 下午
 */
public class Resources {

    public static InputStream getSourceAsStream(String path) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);

        return inputStream;
    }
}
