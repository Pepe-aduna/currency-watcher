package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.C_CONSTANTS;
import com.watcher.cripto.trader.model.TrackData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint(id = "local-binance")
@Service
public class BinanceService {

    @Autowired
    private SyncRestClient syncRestClient;

    @Value("${floki.key}")
    private String key;

    public void getCurrencyData(){
        //syncRestClient.sendPOST();
    }

    public TrackData getAveragePrice(TrackData data){
        //https://api.binance.com/api/v3/avgPrice?symbol=FLOKIUSDT
        /*{
            "mins": 5,
            "price": "0.00004963",
            "closeTime": 1763583660346
        }
        StringBuilder preUrl =  new StringBuilder("https://api.binance.com/api/v3/avgPrice?symbol=");
        for (String s : symbol) {
            preUrl.append(s);
        }*/

        String url = String.format("https://api.binance.com/api/v3/avgPrice?symbol=%s",data.getSymbol());
        String response = syncRestClient.sendGet(url);
        JSONObject json = new JSONObject(response);
        TrackData newData = new TrackData(json);
        newData.setSymbol(data.getSymbol());

        return newData;
    }

    @ReadOperation
    public List<TrackData> getCurrentPrice(String... symbols){
        //api/v3/ticker/price?symbol=["FLOKIUSDT"]
        /*{
            "price": "0.00004963",
            "symbol": 1763583660346
        }*/
        String ss = Arrays.stream(symbols)
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(","));

        String url = String.format("https://api.binance.com/api/v3/ticker/price?symbols=%s",
                URLEncoder.encode("["+ss+"]", StandardCharsets.UTF_8));
        String response = syncRestClient.sendGet(url);
        JSONArray array = new JSONArray(response);
        List<TrackData> list = new ArrayList<>();

        array.forEach( e -> {
            JSONObject j = (JSONObject) e;
            list.add(new TrackData(j.getString(C_CONSTANTS.SYMBOL), j.getDouble(C_CONSTANTS.PRICE)));
        });

        return list;
    }

    public void tickerV3(String... symbol){
        //https://api.binance.com/api/v3/ticker?symbol=FLOKIUSDT&windowSize=14h
    }
}
