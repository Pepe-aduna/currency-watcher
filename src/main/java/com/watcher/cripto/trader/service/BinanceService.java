package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.TrackData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BinanceService {

    @Autowired
    SyncRestClient syncRestClient;

    @Value("${floki.key}")
    String key;

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

    public void tickerV3(String... symbol){
        //https://api.binance.com/api/v3/ticker?symbol=FLOKIUSDT&windowSize=14h
    }
}
