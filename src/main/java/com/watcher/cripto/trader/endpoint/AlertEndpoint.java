package com.watcher.cripto.trader.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watcher.cripto.trader.model.Alert;
import com.watcher.cripto.trader.service.alerts.AlertService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("alert")
public class AlertEndpoint {
    private static final Logger log = LoggerFactory.getLogger(AlertEndpoint.class);

    @Autowired
    AlertService alertService;

    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(path = "/alert/add",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello(@RequestBody String body) throws JsonProcessingException {
        JSONObject payload = new JSONObject(body);
        Alert alert = objectMapper.readValue(body, Alert.class);
        alertService.save(alert);

        log.info("{}",alert);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }
}
