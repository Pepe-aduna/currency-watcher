package com.watcher.cripto.trader.repository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

import static com.watcher.cripto.trader.model.C_CONSTANTS.*;

@Repository
public class CurrencyRepository {

    @Autowired
    JdbcOperations jdbcOperations;

    String borderQ = "SELECT * FROM price_track where  symbol = '%s' AND " +
            "date BETWEEN DATE_SUB(NOW(), INTERVAL %d HOUR) AND NOW() ORDER BY price %s LIMIT 1;";

    public JSONObject getCurrencyBorders(String symbol,Integer hours){
        JSONObject borders = new JSONObject();
        String q = String.format(borderQ, symbol,hours,"DESC");
        jdbcOperations.query(q, (rs, row)->{ borders.put(MAX_PRICE,rs.getDouble(PRICE));
            borders.put(MAX_PRICE_DATE,rs.getTimestamp(DATE));
            return null;});

        q = String.format(borderQ, symbol,hours,"ASC");
        jdbcOperations.query(q, (rs, row)->{ borders.put(MIN_PRICE,rs.getDouble(PRICE));
            borders.put(MIN_PRICE_DATE,rs.getTimestamp(DATE));
            return null;});

        return borders;
    }

}
