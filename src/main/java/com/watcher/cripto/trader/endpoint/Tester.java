package com.watcher.cripto.trader.endpoint;

import com.watcher.cripto.trader.service.PriceStatistics;
import com.watcher.cripto.trader.service.alerts.AlertService;
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
import java.util.ArrayList;
import java.util.List;

@RestController("test")
public class Tester {
    private static final Logger log = LoggerFactory.getLogger(Tester.class);

    @Autowired
    PriceStatistics statistics;

    @Autowired
    PriceAlerts priceAlerts;
    @Autowired
    AlertService alertService;

    @PostMapping(path = "/test",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(@RequestBody String body) {
        JSONObject json = new JSONObject(body);
        String symbol = json.getString("symbol");
        Integer hours = json.getInt("hours");

        statistics.calculateMaxAndMin(symbol,hours);

        return new ResponseEntity<String>("OK!", HttpStatus.OK);
    }

    @PostMapping(path = "/amount",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> amount(@RequestBody String body) {
        JSONObject json = new JSONObject(body);
        List<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal("0.00004461"));
        list.add(new BigDecimal("0.00004471"));
        list.add(new BigDecimal("0.00004481"));
        list.add(new BigDecimal("0.00004500"));
        list.add(new BigDecimal("0.00004481"));
        list.add(new BigDecimal("0.00004480"));
        list.add(new BigDecimal("0.00004475"));
        for (BigDecimal p : list) {
            alertService.evaluate("FLOKIUSDT",p);
        }

        return new ResponseEntity<String>("OK!", HttpStatus.OK);
    }

}
