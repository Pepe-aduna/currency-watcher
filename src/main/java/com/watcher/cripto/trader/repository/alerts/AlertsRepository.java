package com.watcher.cripto.trader.repository.alerts;

import com.watcher.cripto.trader.model.Alert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertsRepository extends CrudRepository<Alert, Integer> {

    @Query("SELECT a FROM Alert a WHERE a.symbol = :symbol and a.type = :type and status = 'enabled'")
    List<Alert> findLastByType(String symbol, String type);
}
