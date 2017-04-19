package com.kat.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YZJLoginService {

    public boolean login(CloseableHttpClient httpclient, String email, String password) throws Exception {
        String loginUrl = "http://yunzhijia.com/space/c/rest/user/login";

        HttpPost httppost = new HttpPost(String.valueOf(loginUrl));

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email",email));
        params.add(new BasicNameValuePair("password",password));
        params.add(new BasicNameValuePair("remember","false"));
        params.add(new BasicNameValuePair("forceToNetwork","false"));
        params.add(new BasicNameValuePair("redirectUrl",""));


        UrlEncodedFormEntity uefEntity;
        uefEntity = new UrlEncodedFormEntity(params, "utf-8");
        httppost.setEntity(uefEntity);
        System.out.println("登录executing request " + httppost.getURI());
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String entityStr = EntityUtils.toString(entity);
        if(entityStr.contains("\"success\":false")) {
            System.out.println("云之家登录失败");
            return false;
        }
        if(entity !=null){
            System.out.println("登录Response content: " + entityStr);
        }
        return true;
    }
}
