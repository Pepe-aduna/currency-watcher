package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.C_CONSTANTS;
import com.watcher.cripto.trader.model.TrackData;
import com.watcher.cripto.trader.repository.BasicRepository;
import com.watcher.cripto.trader.service.alerts.AlertService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Watcher {
    private static final Logger log = LoggerFactory.getLogger(Watcher.class);

    @Autowired
    BinanceService binanceService;
    @Autowired
    BasicRepository repository;
    @Autowired
    AlertService alertService;
    @Autowired
    JSONObject config;

    public void trackCurrencies(String... symbols){
        List<TrackData> tracks = binanceService.getCurrentPrice(symbols);
        repository.saveAll(tracks);
        tracks.forEach( e -> {
            alertService.evaluate(e.getSymbol(),e.getPrice());
        });
    }

    public TrackData getLastData(String symbol){
        return repository.findLastBySymbol(symbol);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void preScheduler(){
        trackCurrencies(config.getString(C_CONSTANTS.SYMBOL));
    }

}
