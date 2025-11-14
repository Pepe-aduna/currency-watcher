package com.aduna.cripto.trader.trader.repository;

import com.aduna.cripto.trader.trader.model.Currency;
import org.springframework.data.repository.CrudRepository;


public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
}
