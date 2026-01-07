package com.watcher.cripto.trader.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SyncRestClient {
    private static final Logger log = LoggerFactory.getLogger(SyncRestClient.class);

    public String sendPOST(String url, List<BasicNameValuePair> headers) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url)
                    .setEntity(new UrlEncodedFormEntity(headers))
                    .build();

            httpclient.execute(httpPost, response -> {
                log.info("{} {}",response.getCode(),response.getReasonPhrase());
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

    public String sendGet(String url){
        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpget = new HttpGet(url);

            //System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getUri());

            final String result = httpclient.execute(httpget, response -> {
                // Process response message and convert it into a value object
                String r = EntityUtils.toString(response.getEntity());
                log.info("RAW RESPONSE -> {} : {}",new StatusLine(response), r);
                return r;
            });
            return result;
        } catch (Exception e) {
            log.error("Sending GET: ",e);
            throw new RuntimeException(e);
        }
    }

}
