package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.TrackData;
import com.watcher.cripto.trader.repository.CurrencyRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Watcher {

    @Autowired
    BinanceService binanceService;

    @Autowired
    CurrencyRepository currencyRepository;

    public void trackCurrencies(String... symbols){
        for (String symbol : symbols) {
            TrackData data = new TrackData(symbol);
            //Optional<TrackData> oData = currencyRepository.findLastByName(data);
            data = binanceService.getAveragePrice(data);

            currencyRepository.save(data);
            //System.out.println("Found: " + oData.isPresent());

        }
    }

}
