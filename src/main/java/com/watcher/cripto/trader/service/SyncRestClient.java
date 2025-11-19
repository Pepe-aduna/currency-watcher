package com.watcher.cripto.trader.service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.StatusLine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SyncRestClient {

    public String sendPOST(String url, List<BasicNameValuePair> headers) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url)
                    .setEntity(new UrlEncodedFormEntity(headers))
                    .build();

            httpclient.execute(httpPost, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                int responseCode = response.getCode();
                final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                String body = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
                return body;
            });
        }

        return "";
    }

    public String sendGet(){
        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpget = new HttpGet("http://httpbin.org/get");

            System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getUri());

            final String result = httpclient.execute(httpget, response -> {
                System.out.println("----------------------------------------");
                System.out.println(httpget + "->" + new StatusLine(response));
                // Process response message and convert it into a value object
                return EntityUtils.toString(response.getEntity());
            });
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }

}
