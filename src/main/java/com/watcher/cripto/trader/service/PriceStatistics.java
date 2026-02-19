package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.C_CONSTANTS;
import com.watcher.cripto.trader.repository.CurrencyRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class PriceStatistics {
    private static final Logger log = LoggerFactory.getLogger(PriceStatistics.class);

    @Autowired
    CurrencyRepository repository;

    BigDecimal threshold = new BigDecimal("0.01"); // 1%

    public void calculateMaxAndMin(String symbol,Integer hours){
        List<JSONObject> data = repository.getCurrencyPeaks(symbol,hours);

        List<JSONObject> localMax = new ArrayList<>();
        List<JSONObject> localMin = new ArrayList<>();

        for (int i = 1; i < data.size() - 1; i++) {
            BigDecimal prev = data.get(i - 1).getBigDecimal(C_CONSTANTS.PRICE);
            BigDecimal curr = data.get(i).getBigDecimal(C_CONSTANTS.PRICE);
            BigDecimal next = data.get(i + 1).getBigDecimal(C_CONSTANTS.PRICE);

            if (curr.compareTo(prev) > 0 &&
                    curr.compareTo(next) > 0 &&
                    isSignificant(curr, prev)) {
                localMax.add(data.get(i));
            }

            if (curr.compareTo(prev) < 0 && curr.compareTo(next) < 0 ) {
                localMin.add(data.get(i));
            }
        }

        log.info("MAX: {}",localMax);
        log.info("MIN: {}",localMin);
    }

    public void identifyMaxAndMin(String symbol){
        //repository.getCurrencyBorders()
    }

    boolean isSignificant(BigDecimal a, BigDecimal b) {
        return a.subtract(b).abs()
                .divide(b, RoundingMode.HALF_UP)
                .compareTo(threshold) >= 0;
    }

}
