package com.watcher.cripto.trader.service;

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

    public void getAveragePrice(String... symbol){
        //https://api.binance.com/api/v3/avgPrice?symbol=FLOKIUSDT
    }

    public void tickerV3(String... symbol){
        //https://api.binance.com/api/v3/ticker?symbol=FLOKIUSDT&windowSize=14h
    }
}
