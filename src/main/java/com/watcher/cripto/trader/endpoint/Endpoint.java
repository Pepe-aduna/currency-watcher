package com.watcher.cripto.trader.endpoint;

import com.watcher.cripto.trader.service.CurrencyAssistant;
import com.watcher.cripto.trader.service.Watcher;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoint {

    @Autowired
    Watcher watcher;
    @Autowired
    CurrencyAssistant assistant;

    @PostMapping(path = "/hello",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello(@RequestBody String name) {

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(path = "/watcher",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> watcher(@RequestBody String body) {
        JSONObject json = new JSONObject(body);
        String symbol = json.getString("symbol");

        watcher.trackCurrencies(symbol);
        String message = assistant.preScheduler();
        JSONObject response = new JSONObject();
        response.put("message",message);
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

}
