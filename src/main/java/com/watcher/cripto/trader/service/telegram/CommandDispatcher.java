package com.watcher.cripto.trader.service.telegram;

import com.watcher.cripto.trader.service.telegram.command.BotCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandDispatcher {
    private static final Logger log = LoggerFactory.getLogger(CommandDispatcher.class);

    private final Map<String, BotCommand> commandMap;

    public CommandDispatcher(List<BotCommand> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toMap(
                        BotCommand::getName,
                        Function.identity()
                ));
    }

    public String dispatch(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return "Invalid message";
        }

        String text = update.getMessage().getText();
        String command = text.split(" ")[0];

        BotCommand botCommand = commandMap.get(command);
        if (botCommand != null) {
            return botCommand.execute(update);
        } else {
            // unknown command handling
            return "Hello!";
        }
    }

}
