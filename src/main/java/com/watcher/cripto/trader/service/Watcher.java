package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.C_CONSTANTS;
import com.watcher.cripto.trader.model.TrackData;
import com.watcher.cripto.trader.repository.BasicRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Watcher {

    @Autowired
    BinanceService binanceService;
    @Autowired
    BasicRepository repository;
    @Autowired
    JSONObject config;

    public void trackCurrencies(String... symbols){
        List<TrackData> tracks = binanceService.getCurrentPrice(symbols);
        repository.saveAll(tracks);
    }

    public TrackData getLastData(String symbol){
        return repository.findLastBySymbol(symbol);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void preScheduler(){
        trackCurrencies(config.getString(C_CONSTANTS.SYMBOL));
    }

}
