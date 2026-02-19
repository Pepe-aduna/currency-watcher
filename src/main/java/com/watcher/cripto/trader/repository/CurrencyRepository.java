package com.watcher.cripto.trader.repository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.watcher.cripto.trader.model.C_CONSTANTS.*;

@Repository
public class CurrencyRepository {

    @Autowired
    JdbcOperations jdbcOperations;

    String borderQ = "SELECT * FROM price_track where  symbol = '%s' AND " +
            "date BETWEEN DATE_SUB(NOW(), INTERVAL %d MINUTE) AND NOW() ORDER BY price %s LIMIT 1;";

    String peaksQ = "SELECT id, symbol, price, date, FROM price_track " +
            "WHERE symbol = '%s' and date BETWEEN DATE_SUB(NOW(), INTERVAL %d HOUR) AND NOW() " +
            "ORDER BY date ASC;";

    String allByDate = "SELECT price, date, id FROM price_track " +
            "WHERE symbol = '%s' AND date >= '%s' ORDER BY date %s;";

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

    public List<JSONObject> getCurrencyPeaks(String symbol,Integer hours){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<JSONObject> rows = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY,-hours);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);

        String q = String.format(allByDate, symbol,format.format(cal.getTime()),"ASC");
        jdbcOperations.query(q, (rs, row)->{ rows.add(new JSONObject().put(PRICE,rs.getBigDecimal(PRICE))
                .put(DATE,rs.getTimestamp(DATE))
                .put(_ID,rs.getLong("id"))
            );
            return null;});

        return rows;
    }

}
