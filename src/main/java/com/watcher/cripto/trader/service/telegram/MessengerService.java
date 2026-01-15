package com.watcher.cripto.trader.service.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class MessengerService implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessengerService.class);

    private final TelegramClient telegramClient;
    String token;

    @Autowired
    CommandDispatcher dispatcher;

    public MessengerService(@Value("${telegram.token}")
                            String token) {
        this.token = token;
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        String response = dispatcher.dispatch(update);
        long chatId = update.getMessage().getChatId();

        sendMessage(chatId,response);
    }

    public void consumeV1(Update update) {
/*        if (update.hasChannelPost()) {
            Message msg = update.getChannelPost();
            Long chatId = msg.getChatId();

            log.info("CHANNEL CHAT ID: {}", chatId);
        }*/
    }

    public void sendMessage(Long chat_id,String message_text){
            log.info("{} - {}",chat_id, message_text);

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .parseMode("HTML")
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                log.error("Sending message: ",e);
            }

    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}