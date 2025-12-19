package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.C_CONSTANTS;
import com.watcher.cripto.trader.model.TrackData;
import com.watcher.cripto.trader.repository.BasicRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Watcher {

    @Autowired
    BinanceService binanceService;
    @Autowired
    BasicRepository currencyRepository;
    @Autowired
    JSONObject config;

    public void trackCurrencies(String... symbols){
        for (String symbol : symbols) {
            TrackData data = new TrackData(symbol);
            //Optional<TrackData> oData = currencyRepository.findLastByName(data);
            data = binanceService.getAveragePrice(data);
            currencyRepository.save(data);
            System.out.println(String.format("%s - %.8f - %s",data.getSymbol(),data.getPrice(),data.getDate().toString()));
        }
    }

    public TrackData getLastData(String symbol){
        return currencyRepository.findLastBySymbol(symbol);
    }

    @Scheduled(fixedRate = 60000)
    public void preScheduler(){
        trackCurrencies(config.getString(C_CONSTANTS.SYMBOL));
    }

}
