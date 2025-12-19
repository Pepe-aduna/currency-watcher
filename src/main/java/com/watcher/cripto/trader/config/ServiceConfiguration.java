package com.watcher.cripto.trader.config;

import com.watcher.cripto.trader.model.ConfigurationData;
import com.watcher.cripto.trader.repository.BasicRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.watcher.cripto.trader.model.C_CONSTANTS.WATCHES;

@Configuration
public class ServiceConfiguration {

    @Autowired
    BasicRepository repository;

    @Bean(name = "watcherConfiguration")
    public JSONObject watcherConfiguration(){
        ConfigurationData configData = repository.getConfiguration(WATCHES);
        return new JSONObject(configData.getValue());
    }
}
