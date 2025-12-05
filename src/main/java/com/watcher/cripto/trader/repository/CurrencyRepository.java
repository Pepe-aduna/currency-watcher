package com.watcher.cripto.trader.repository;

import com.watcher.cripto.trader.model.Currency;
import com.watcher.cripto.trader.model.TrackData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CurrencyRepository extends CrudRepository<TrackData, Integer> {

    @Query("SELECT t FROM TrackData t WHERE t.symbol = :symbol order by t_id DESC LIMIT 1")
    Optional<TrackData> findLastBySymbol(TrackData data);
}
