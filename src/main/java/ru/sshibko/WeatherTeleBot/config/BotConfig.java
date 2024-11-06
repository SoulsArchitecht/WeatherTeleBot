package ru.sshibko.WeatherTeleBot.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@RequiredArgsConstructor
@Configuration
public class BotConfig {

    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.name}")
    private String name;
    @Value("${telegram.bot.webhook-path}")
    private String webhookPath;
    @Value("${telegram.bot.webhookUrl}")
    private String webhookUrl;
    @Value("#{new Boolean('${telegram.bot.enableWebhook}')}")
    private boolean enableWebhook;

}
