package com.watcher.cripto.trader.repository;

import com.watcher.cripto.trader.model.ConfigurationData;
import com.watcher.cripto.trader.model.TrackData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BasicRepository extends CrudRepository<TrackData, Integer> {

    @Query("SELECT t FROM TrackData t WHERE t.symbol = :symbol order by t_id DESC LIMIT 1")
    TrackData findLastBySymbol(String symbol);

    @Query("SELECT c FROM ConfigurationData c WHERE c.name = :name")
    ConfigurationData getConfiguration(String name);

}
