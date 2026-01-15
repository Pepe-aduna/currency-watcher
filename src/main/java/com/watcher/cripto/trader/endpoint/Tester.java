package com.watcher.cripto.trader.endpoint;

import com.watcher.cripto.trader.service.PriceStatistics;
import com.watcher.cripto.trader.service.SyncRestClient;
import com.watcher.cripto.trader.service.alerts.PriceAlerts;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController("test")
public class Tester {
    private static final Logger log = LoggerFactory.getLogger(Tester.class);

    @Autowired
    PriceStatistics statistics;

    @Autowired
    PriceAlerts priceAlerts;

    @PostMapping(path = "/test",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(@RequestBody String body) {
        JSONObject json = new JSONObject(body);
        String symbol = json.getString("symbol");
        Integer hours = json.getInt("hours");

        statistics.calculateMaxAndMin(symbol,hours);

        return new ResponseEntity<String>("OK!", HttpStatus.OK);
    }

}
