package ru.sshibko.WeatherTeleBot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sshibko.WeatherTeleBot.service.KeyboardService;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

import java.util.List;

import static ru.sshibko.WeatherTeleBot.data.QueryData.MAIN;

@Service
@RequiredArgsConstructor
public class MessageHandler {

    private final KeyboardService keyboardService;

    public BotApiMethod<?> answer(Message message, MyWeatherBot bot) {
        return SendMessage.builder()
                .text("Бот не умеет общаться текстом. Только через кнопки в меню")
                .chatId(message.getChatId())
                .replyMarkup(
                    keyboardService.getInlineKeyboard(
                            List.of("На главную"),
                            List.of(1),
                            List.of(MAIN.name())
                    )
                )
                .build();
    }
}
