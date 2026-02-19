package com.watcher.cripto.trader.service.alerts;

import com.watcher.cripto.trader.model.Alert;
import com.watcher.cripto.trader.model.AlertEntity;
import com.watcher.cripto.trader.repository.alerts.AlertsRepository;
import com.watcher.cripto.trader.service.Watcher;
import com.watcher.cripto.trader.service.telegram.MessengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AlertService {
    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    @Autowired
    MessengerService messenger;
    @Autowired
    AlertsRepository repository;

    private Map<BigDecimal, Boolean> alertsStatus = new ConcurrentHashMap<>();
    private Map<String, Alert> lastDeltaPrice = new ConcurrentHashMap<>();

    String [] types = {"delta","fixed"};

    String messageAmount = "%s: %.8f \nlast: %.8f";

    public AlertService(){
        alertsStatus.put(new BigDecimal("0.00005500"),false);
    }

    public void save(AlertEntity alert){
        //Al guardar o modificar debuscar y deshabilitar alarmas de otro tipo.
        repository.save(alert);
    }

    public void evaluate(String symbol, BigDecimal price) {
        /*
          Buscar una forma de optimizar las alertas, por ejemplo por rango de precios.
         */
        for (String type : types) {
            List<AlertEntity> alerts = repository.findAllBySymbolAndType(symbol,type);
            for (AlertEntity a : alerts) {
                lastDeltaPrice.putIfAbsent(a.getName(),
                        new Alert(a.getName(),price,a.getSymbol(),type));
                Alert lastAlert = lastDeltaPrice.get(a.getName());
                BigDecimal diff = price.subtract(lastAlert.getLastPrice());
                if(diff.abs().compareTo(a.getDeltaAmount()) >= 0){
                    log.info("Alert here: {} - {} - {}",price.toPlainString(),lastAlert.getLastPrice().toPlainString(),diff.toPlainString());
                    lastDeltaPrice.put(a.getName(),
                            new Alert(a.getName(),price,a.getSymbol(),type));

                    String text = messageAmount.formatted(symbol, price,lastAlert.getLastPrice());
                    messenger.sendMessage(Long.valueOf(a.getNotification_channel()),text);
                }
            }
        }
    }

    public void fixed(String symbol, BigDecimal price) {
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

}
