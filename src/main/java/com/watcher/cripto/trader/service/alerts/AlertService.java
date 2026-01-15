package com.watcher.cripto.trader.service.alerts;

import com.watcher.cripto.trader.model.Alert;
import com.watcher.cripto.trader.repository.alerts.AlertsRepository;
import com.watcher.cripto.trader.service.telegram.MessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AlertService {

    @Autowired
    MessengerService messenger;
    @Autowired
    AlertsRepository repository;

    private final Map<String, Instant> sentAlerts = new ConcurrentHashMap<>();
    private Map<BigDecimal, Boolean> alertsStatus = new ConcurrentHashMap<>();
    private final Duration ttl = Duration.ofMinutes(10);

    public AlertService(){
        alertsStatus.put(new BigDecimal("0.00005100"),false);
        alertsStatus.put(new BigDecimal("0.00005150"),false);
        alertsStatus.put(new BigDecimal("0.00005200"),false);
        alertsStatus.put(new BigDecimal("0.00005300"),false);
        alertsStatus.put(new BigDecimal("0.00005400"),false);
        alertsStatus.put(new BigDecimal("0.00005500"),false);
        alertsStatus.put(new BigDecimal("0.00005600"),false);
        alertsStatus.put(new BigDecimal("0.00005700"),false);
        alertsStatus.put(new BigDecimal("0.00005800"),false);
        alertsStatus.put(new BigDecimal("0.00005900"),false);
    }

    public void save(Alert alert){
        repository.save(alert);
    }

    public void evaluate(String symbol, BigDecimal price) {
        for (Map.Entry<BigDecimal, Boolean> entry : alertsStatus.entrySet()) {
            BigDecimal k  = entry.getKey();
            if(price.compareTo(k) >= 0 && !entry.getValue()){
                messenger.sendMessage(-1003669461288L,
                        "%s - Surpassed: %.8f".formatted(symbol,price));
                alertsStatus.put(k,true);
                break;
            }else if(price.compareTo(k) < 0){
                alertsStatus.put(k,false);
            }
        }
    }

    public void priceVariation(String symbol,BigDecimal price){

    }

    public boolean shouldSend(String alertKey) {
        Instant now = Instant.now();
        sentAlerts.entrySet().removeIf(e ->
                e.getValue().isBefore(now.minus(ttl)));

        return sentAlerts.putIfAbsent(alertKey, now) == null;
    }
}
