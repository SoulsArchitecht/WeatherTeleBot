package ru.sshibko.WeatherTeleBot.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sshibko.WeatherTeleBot.config.BotConfig;
import ru.sshibko.WeatherTeleBot.service.UpdateDispatcher;

@Component
public class MyWeatherBot extends TelegramWebhookBot {

    private final BotConfig botConfig;
    private final UpdateDispatcher updateDispatcher;


    public MyWeatherBot(BotConfig botConfig, UpdateDispatcher updateDispatcher) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.updateDispatcher = updateDispatcher;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return updateDispatcher.distribute(update, this);
    }

    @Override
    public String getBotPath() {
        return botConfig.getWebhookUrl();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

}
