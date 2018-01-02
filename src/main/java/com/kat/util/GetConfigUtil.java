package com.kat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetConfigUtil {

    private static Map<String, String> proMap = new HashMap<String, String>();
    static {
        // File file = new File("src/api_auth_config.properties");
        InputStream is = GetConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
        Properties pro = new Properties();
        try {
            // is = new FileInputStream(file);
            pro.load(is);
            Enumeration<?> e = pro.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = pro.getProperty(key);
                proMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is!=null){
                    is.close();// 千万别忘了关闭资源哦！
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, String> getProMap() {
        return proMap;
    }
}
