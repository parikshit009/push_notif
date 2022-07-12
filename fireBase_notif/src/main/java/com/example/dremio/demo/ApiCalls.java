package com.example.dremio.demo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ApiCalls {

    public static String sendPOST_Prod(String url, Signature ksi_timeStamp, String body) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);

        StringEntity entity = new StringEntity(body);
        post.setEntity(entity);
        post.setHeader("cache-control", "no-cache");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("postman-token", "e934352e-0fec-bb89-0210-b76c29d36d2a");
        post.setHeader("wm_consumer.id", "88d42ddf-401f-42ee-903d-7f949a289dde");
        post.setHeader("wm_consumer.intimestamp", ksi_timeStamp.intimestamp);
        post.setHeader("wm_consumer.user_id", "55b4834d-ed37-40fd-b8bb-2da31e9aad9f");
        post.setHeader("wm_sec.auth_signature", ksi_timeStamp.token);
        post.setHeader("wm_svc.env", "prod");
        post.setHeader("wm_svc.name", "MX_SINGLE_PROFILE_SERVICE");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }

    public static String sendPOST(String url,String body,String auth) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);

        StringEntity entity = new StringEntity(body);

        post.setEntity(entity);

        post.setHeader("Content-type", "application/json");
        if(auth.length()>0)
            post.setHeader("authorization", auth);


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
            //System.out.println(response.getEntity());
        }

        return result;
    }

    public static String sendGet(String url,String auth) throws IOException {

        String result = "";
        HttpGet get = new HttpGet(url);

        get.setHeader("Content-type", "application/json");
        if(auth.length()>0)
            get.setHeader("authorization", auth);


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {

            result = EntityUtils.toString(response.getEntity());

        }

        return result;
    }
}
