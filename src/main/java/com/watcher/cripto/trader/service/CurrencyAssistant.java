package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.ConfigurationData;
import com.watcher.cripto.trader.model.TrackData;
import com.watcher.cripto.trader.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.watcher.cripto.trader.model.C_CONSTANTS.*;

@Service
public class CurrencyAssistant {

    @Autowired
    Watcher watcher;
    @Autowired
    CurrencyRepository repository;
    @Autowired
    MessengerService messengerService;

    JSONObject config = new JSONObject();

    @PostConstruct
    public void setUp(){
        ConfigurationData configData = repository.getConfiguration(WATCHES);
        JSONObject pre = new JSONObject(configData.getValue());
        config.put(SYMBOL,pre.getString(SYMBOL));
        config.put(NOTIFICATION_ID,pre.getLong(NOTIFICATION_ID));
    }

    @Scheduled(cron = "0,30 * * * *")
    public void preScheduler(){
        TrackData data = watcher.getLastData(config.getString(SYMBOL));
        String message = String.format("Symbol: %s \n Price: %.8f \n Id: %d",data.getSymbol(),data.getPrice(),data.getT_id());
        messengerService.sendMessage(config.getLong(NOTIFICATION_ID),message);
    }
}
