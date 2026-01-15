package com.watcher.cripto.trader.service.alerts;

import com.watcher.cripto.trader.service.SyncRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class PriceAlerts {
    private static final Logger log = LoggerFactory.getLogger(PriceAlerts.class);

    TreeSet<BigDecimal> alerts = new TreeSet<>();

    public String evaluate(BigDecimal current, BigDecimal previous) {
        for (BigDecimal pAlert : alerts) {
            if(current.compareTo(pAlert) >= 0 && previous.compareTo(pAlert) < 0){
                return "surpassed";
            }

            if(current.compareTo(pAlert) < 0 && previous.compareTo(pAlert) >= 0){
                return "drops below";
            }
        }
        return null;
    }
}
