package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.TrackData;
import com.watcher.cripto.trader.repository.CurrencyRepository;
import com.watcher.cripto.trader.service.telegram.MessengerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import static com.watcher.cripto.trader.model.C_CONSTANTS.*;
import static java.lang.String.format;

@Endpoint(id = "assistant")
@Service
public class CurrencyAssistant {
    private static final Logger log = LoggerFactory.getLogger(CurrencyAssistant.class);

    @Autowired
    Watcher watcher;
    @Autowired
    MessengerService messengerService;
    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    JSONObject config;

    String sCurrent = "%s: %.8f\n";
    String sRanges = "<b>%s %d:</b> %.8f : %s\n";

    @ReadOperation
    @Scheduled(cron = "0 0/20 * * * ?")
    public String preScheduler(){
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        StringBuilder builder = new StringBuilder();
        String symbol = config.getString(SYMBOL);
        JSONArray ranges = config.getJSONArray(RANGES);

        TrackData data = watcher.getLastData(symbol);
        builder.append(format(sCurrent,symbol,data.getPrice()));

        ranges.forEach(e -> {
            Integer range = (Integer) e * 60;
            JSONObject json = currencyRepository.getCurrencyBorders(symbol,range);
            builder.append(format(sRanges, "max price",range,json.getDouble(MAX_PRICE),
                    format.format(json.get(MAX_PRICE_DATE))));

            builder.append(format(sRanges, MIN_PRICE,range,json.getDouble(MIN_PRICE),
                    format.format(json.get(MIN_PRICE_DATE))));
        });

        String message = builder.toString();
        messengerService.sendMessage(config.getLong(NOTIFICATION_ID),message);
        return message;
    }

}
