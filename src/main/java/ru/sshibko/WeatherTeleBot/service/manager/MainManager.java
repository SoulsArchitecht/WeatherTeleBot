package ru.sshibko.WeatherTeleBot.service.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sshibko.WeatherTeleBot.service.KeyboardService;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

import java.util.List;

import static ru.sshibko.WeatherTeleBot.data.QueryData.FORECAST;
import static ru.sshibko.WeatherTeleBot.data.TextConstant.MAIN_INVITE;

@Service
@RequiredArgsConstructor
public class MainManager {

    private final KeyboardService keyboardService;

    public BotApiMethod<?> answerCommand(Message message, MyWeatherBot bot) {
        return SendMessage.builder()
                .text(MAIN_INVITE)
                .chatId(message.getChatId())
                .replyMarkup(
                        keyboardService.getInlineKeyboard(
                                List.of("ПРОГНОЗ"),
                                List.of(1),
                                List.of(FORECAST.name())
                        )
                )
                .build();
    }

    public BotApiMethod<?> answerQuery(CallbackQuery callbackQuery, String[] data, MyWeatherBot bot) {
        return EditMessageText.builder()
                .text(MAIN_INVITE)
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup(
                        keyboardService.getInlineKeyboard(
                                List.of("ПРОГНОЗ"),
                                List.of(1),
                                List.of(FORECAST.name())
                        )
                )
                .build();
    }
}
