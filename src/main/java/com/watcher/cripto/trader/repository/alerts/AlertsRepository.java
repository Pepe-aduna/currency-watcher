package com.watcher.cripto.trader.repository.alerts;

import com.watcher.cripto.trader.model.AlertEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertsRepository extends CrudRepository<AlertEntity, Integer> {

    @Query("SELECT a FROM AlertEntity a WHERE a.symbol = :symbol and a.type = :type and status = 'enabled'")
    List<AlertEntity> findAllBySymbolAndType(String symbol, String type);

}
