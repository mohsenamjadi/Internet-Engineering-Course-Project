package com.internet.engineering.IECA8.utils.HTTPRequestHandler;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTPRequestHandler {

    public static int getStatusCode(String uri) throws Exception {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            return response.getStatusLine().getStatusCode();
        }

    }

    public static void getRequest(String uri, String json) throws Exception {
        Gson gson          = new Gson();
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost     post          = new HttpPost(uri);
        StringEntity postingString = new StringEntity(json);
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        httpClient.execute(post);
    }

    public static String getRequest(String uri) throws Exception {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
//            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
//            System.out.println(headers);

            String result = "";
            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
//                System.out.println(result);
            }
            return result;
        }
    }
}
