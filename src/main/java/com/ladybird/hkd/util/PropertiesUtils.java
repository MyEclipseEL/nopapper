package com.ladybird.hkd.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
public class PropertiesUtils {

    public String getUrlValue(String urlName, String propertiesName) {
        String url = null;
        Properties properties = new Properties();
        try {
            //读取属性文件
            ClassLoader classLoader = PropertiesUtils.class.getClassLoader();
            InputStream in = classLoader.getResourceAsStream(propertiesName + ".properties");
            //加载属性列表
            properties.load(in);
            Iterator<String> it = properties.stringPropertyNames().iterator();
            while (it.hasNext()) {
                if (it.next().equals(urlName)) {
                    url = properties.getProperty(urlName);
                }
            }
            in.close();
        } catch (Exception e) {

        }
        return url;
    }
}