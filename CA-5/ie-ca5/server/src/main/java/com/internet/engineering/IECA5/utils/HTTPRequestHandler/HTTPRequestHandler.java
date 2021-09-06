package com.internet.engineering.IECA5.utils.HTTPRequestHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
