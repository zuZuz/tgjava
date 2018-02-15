package com.zuZuz.XusuGateBot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class XusuGateBot extends TelegramLongPollingBot {
    private final static Logger logger = Logger.getLogger(XusuGateBot.class);

    private String token = null;
    private String name = null;

    Map<Integer, String> users = new HashMap<Integer, String>();

    private void loadProperties() {
        Properties props = new Properties();
        InputStream input = null;

        try {
            props.load(this.getClass().getClassLoader().
                    getResourceAsStream("xusugatebot.properties"));

            this.token = props.getProperty("token");
            this.name = props.getProperty("name");
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(1);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        if (name == null) {
            loadProperties();
        }

        return name;
    }

    @Override
    public String getBotToken() {
        if (token == null) {
            loadProperties();
        }

        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            Integer userId = update.getMessage().getFrom().getId();
            String text = update.getMessage().getText();

            if (!users.containsKey(userId)) {
                users.put(userId, null);
            }

            XusuMessage xusuMessage = null;
            try {
                xusuMessage = XusuRequest.doRequest(text, users.get(userId));
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            if (xusuMessage == null) {
                return;
            }

            if (users.get(userId) == null) {
                users.put(userId, xusuMessage.getUid());
            }

            try {
                SendMessage msg = new SendMessage()
                        .setChatId(chatId)
                        .setText(new String(xusuMessage.getText().getBytes("UTF-8"), "cp866"));
                logger.info(xusuMessage.getText());
                execute(msg);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
