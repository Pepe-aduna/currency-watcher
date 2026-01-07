package com.watcher.cripto.trader.service;

import com.watcher.cripto.trader.model.C_CONSTANTS;
import com.watcher.cripto.trader.repository.CurrencyRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PriceStadistics {

    @Autowired
    CurrencyRepository repository;

    BigDecimal threshold = new BigDecimal("0.01"); // 1%

    public void calculateMaxAndMin(String symbol,Integer hours){
        List<JSONObject> data = repository.getCurrencyPeaks(symbol,hours);


        List<JSONObject> localMax = new ArrayList<>();
        List<JSONObject> localMin = new ArrayList<>();

        for (int i = 1; i < data.size() - 1; i++) {
            BigDecimal prev = BigDecimal.valueOf(data.get(i - 1).getDouble(C_CONSTANTS.PRICE));
            BigDecimal curr = BigDecimal.valueOf(data.get(i).getDouble(C_CONSTANTS.PRICE));
            BigDecimal next = BigDecimal.valueOf(data.get(i + 1).getDouble(C_CONSTANTS.PRICE));

            if (curr.compareTo(prev) > 0 && curr.compareTo(next) > 0) {
                localMax.add(data.get(i));
            }

            if (curr.compareTo(prev) < 0 && curr.compareTo(next) < 0 &&
                    isSignificant(curr, prev)) {
                localMin.add(data.get(i));
            }
        }

    }

    boolean isSignificant(BigDecimal a, BigDecimal b) {
        return a.subtract(b).abs()
                .divide(b, RoundingMode.HALF_UP)
                .compareTo(threshold) >= 0;
    }

}
