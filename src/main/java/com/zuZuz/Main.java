package com.zuZuz;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import com.zuZuz.XusuGateBot.XusuGateBot;

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            ApiContextInitializer.init();
            TelegramBotsApi api = new TelegramBotsApi();
            logger.info("Running bot");

            try {
                api.registerBot(new XusuGateBot());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
