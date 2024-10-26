package ru.sshibko.WeatherTeleBot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sshibko.WeatherTeleBot.data.QueryData;
import ru.sshibko.WeatherTeleBot.service.manager.ForecastManager;
import ru.sshibko.WeatherTeleBot.service.manager.MainManager;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackQueryHandler {

    MainManager mainManager;
    ForecastManager forecastManager;

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, MyWeatherBot bot) {

        String[] data = callbackQuery.getData().split("_");
        QueryData queryData;
        try {
            queryData = QueryData.valueOf(data[0]);
        } catch (Exception e) {
            log.error("Unsupported query data received: " + callbackQuery.getData());
            return null;
        }

        switch (queryData) {
            case MAIN -> {
                return mainManager.answerQuery(callbackQuery, data, bot);
            }
            case EMPTY -> {
                return AnswerCallbackQuery.builder()
                        .callbackQueryId(callbackQuery.getId())
                        .text("На эту кнопку не назначено действие")
                        .build();
            }
            case FORECAST -> {
                return forecastManager.answerQuery(callbackQuery, data, bot);
            }
        }
        throw new UnsupportedOperationException();
    }
}
