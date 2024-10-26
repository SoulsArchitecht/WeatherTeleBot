package ru.sshibko.WeatherTeleBot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

@RestController
@RequiredArgsConstructor
public class BotController {

    private final MyWeatherBot bot;

    @PostMapping("/")
    public BotApiMethod<?> updateListener (@RequestBody Update update) {

        return bot.onWebhookUpdateReceived(update);
    }

    @PostMapping("/webhook")
    public String setWebhook() {
        return "Webhook has been set";
    }
}
