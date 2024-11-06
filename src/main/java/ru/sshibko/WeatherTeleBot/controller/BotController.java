package ru.sshibko.WeatherTeleBot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    //TODO Best practice method to activate/deactivate webhook with telegram api
/*    @GetMapping("{path}/bot${bot.getToken}/setWebhook")
    public ResponseEntity<?> setWebhook(@RequestParam(value = "url") String webhookUrl, @PathVariable MyWeatherBot bot, @PathVariable String path) {
        //bot.setWebhook(webhookUrl);
        return ResponseEntity.ok("Webhook was set successfully");
    }*/
}
