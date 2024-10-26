package ru.sshibko.WeatherTeleBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.sshibko.WeatherTeleBot.config.BotConfig;

@SpringBootApplication
@EnableConfigurationProperties(BotConfig.class)
public class WeatherTeleBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherTeleBotApplication.class, args);
	}

}
