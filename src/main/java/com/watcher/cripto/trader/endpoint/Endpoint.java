package com.watcher.cripto.trader.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoint {

    @PostMapping(path = "/hello",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello(@RequestBody String name) {

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
}
