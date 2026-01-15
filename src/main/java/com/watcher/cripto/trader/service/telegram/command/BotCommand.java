package com.watcher.cripto.trader.service.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommand {

    String getName();        // e.g. "/start"
    String getDescription(); // for /help
    String execute(Update update);
}

