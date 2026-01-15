package com.watcher.cripto.trader.service.telegram.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AlertCommand implements BotCommand{
    @Override
    public String getName() {
        return "/alert";
    }

    @Override
    public String getDescription() {
        return "Alerts commands";
    }

    @Override
    public String execute(Update update) {
        return "Alerts!";
    }
}
