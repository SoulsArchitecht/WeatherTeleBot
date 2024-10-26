package ru.sshibko.WeatherTeleBot.service.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sshibko.WeatherTeleBot.dto.WeatherData;
import ru.sshibko.WeatherTeleBot.dto.WeatherResponse;
import ru.sshibko.WeatherTeleBot.service.KeyboardService;
import ru.sshibko.WeatherTeleBot.service.WeatherService;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

import java.util.List;

import static ru.sshibko.WeatherTeleBot.data.QueryData.*;
import static ru.sshibko.WeatherTeleBot.data.TextConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastManager {

    private final KeyboardService keyboardService;

    private final WeatherService weatherService;

    public BotApiMethod<?> answerCommand(Message message, MyWeatherBot bot) {

        return SendMessage.builder()
                .text(INPUT_CITY_AND_VAR_INVITE)
                .chatId(message.getChatId())
                .replyMarkup(
                        keyboardService.getInlineKeyboard(
                                List.of("Ввести город"),
                                List.of(1),
                                List.of(FORECAST_CITY_INPUT.name())
                        )
                )
                .build();
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery, MyWeatherBot bot) {
        return EditMessageText.builder()
                .text(INPUT_CITY_AND_VAR_INVITE)
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup(
                        keyboardService.getInlineKeyboard(
                                List.of("Ввести город"),
                                List.of(1),
                                List.of(FORECAST_CITY_INPUT.name())
                        )
                )
                .build();
    }

    public BotApiMethod<?> answerQuery(CallbackQuery callbackQuery, String[] data, MyWeatherBot bot) {
        if (data.length == 1) {
            return mainMenu(callbackQuery, bot);
        }
        switch (data[1]) {
            case "c" -> {
                return cityInput(callbackQuery, data.length == 2 ? "" : data[2], bot);
            }
            case "e" -> {
                if (data.length == 2) {
                    return AnswerCallbackQuery.builder()
                            .callbackQueryId(callbackQuery.getId())
                            .text("Необходимо ввести название города!")
                            .build();
                }
                return cityInput(callbackQuery, data[2], bot);
            }
            case "s" -> {
                return sendResult(callbackQuery, data[2], data[3], bot);
            }
        }
        log.error("Unsupported query: " + callbackQuery);

        return null;
    }

    private BotApiMethod<?> sendResult(CallbackQuery callbackQuery, String type, String cityName, MyWeatherBot bot) {

        try {
            bot.execute(
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text("Запрос отправлен, пожалуйста, подождите")
                            .build()
            );
            bot.execute(
                    DeleteMessage.builder()
                            .messageId(callbackQuery.getMessage().getMessageId())
                            .chatId(callbackQuery.getMessage().getChatId())
                            .build()
            );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

        WeatherResponse weatherResponse = "h".equals(type)
                ? weatherService.getHoursByCityName(cityName)
                : weatherService.getDailyByCityName(cityName);
        StringBuilder sb = new StringBuilder();
        int number = 1;
        for (WeatherData weatherData : weatherResponse.getWeatherData()) {
            sb.append("\uD83D\uDCCD +").append(number).append(")\n")
                    .append("Температура: ").append(weatherData.getTemperature()).append("\n")
                    .append("Вероятность осадков: ").append(weatherData.getPop())
                    .append("Скорость ветра: ").append(weatherData.getWindSpeed()).append("\n")
                    .append("Описание: ").append(weatherData.getWeather().getDescription()).append("\n\n");
            number++;
        }

        return SendMessage.builder()
                .text(sb.toString())
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(
                        keyboardService.getInlineKeyboard(
                                List.of("На главную"),
                                List.of(1),
                                List.of(MAIN.name())
                        )
                )
                .build();
    }

    private BotApiMethod<?> enteredCity(CallbackQuery callbackQuery, String cityName, MyWeatherBot bot) {

        return EditMessageText.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .text(INPUT_VAR_INVITE).replyMarkup(
                        keyboardService.getInlineKeyboard(
                                List.of("24 часа", "7 дней"),
                                List.of(2),
                                List.of(FORECAST_SEND_RESULT.name() + "h" + cityName, FORECAST_SEND_RESULT.name() + "d" + cityName)
                        )
                )
                .build();
    }

    private BotApiMethod<?> cityInput(CallbackQuery callbackQuery, String currentInput, MyWeatherBot bot) {
        return EditMessageText.builder()
                .text("Введите название города используя интерактивную клавиатуру")
                .replyMarkup(
                        cityInputKeyboard(currentInput)
                )
                .messageId(callbackQuery.getMessage().getMessageId())
                .chatId(callbackQuery.getMessage().getChatId())
                .build();
    }

    private InlineKeyboardMarkup cityInputKeyboard(String currentInput) {

        return keyboardService.getInlineKeyboard(
                List.of(
                        !currentInput.isEmpty() ? currentInput : "Поле ввода",
                        "Q", "W", "E", "R", "T", "Y", "U", "I",
                        "O", "P", "A", "S", "D", "F", "G", "H",
                        "J", "K", "L", "Z", "X", "C", "V", "B",
                        "N", "M", "-", "\uD83D\uDEAB", "\uD83D\uDEAB", "\uD83D\uDEAB", "\uD83D\uDEAB", "\uD83D\uDEAB",
                        "Ввод", "Стереть"
                ),
                List.of(1, 8, 8, 8, 8, 2),
                List.of(
                        EMPTY.name(),
                        FORECAST_CITY_INPUT.name() + currentInput + "Q", FORECAST_CITY_INPUT.name() + currentInput + "W",
                        FORECAST_CITY_INPUT.name() + currentInput + "E", FORECAST_CITY_INPUT.name() + currentInput + "R",
                        FORECAST_CITY_INPUT.name() + currentInput + "T", FORECAST_CITY_INPUT.name() + currentInput + "Y",
                        FORECAST_CITY_INPUT.name() + currentInput + "U", FORECAST_CITY_INPUT.name() + currentInput + "I",
                        FORECAST_CITY_INPUT.name() + currentInput + "O", FORECAST_CITY_INPUT.name() + currentInput + "P",
                        FORECAST_CITY_INPUT.name() + currentInput + "A", FORECAST_CITY_INPUT.name() + currentInput + "S",
                        FORECAST_CITY_INPUT.name() + currentInput + "D", FORECAST_CITY_INPUT.name() + currentInput + "F",
                        FORECAST_CITY_INPUT.name() + currentInput + "G", FORECAST_CITY_INPUT.name() + currentInput + "H",
                        FORECAST_CITY_INPUT.name() + currentInput + "J", FORECAST_CITY_INPUT.name() + currentInput + "K",
                        FORECAST_CITY_INPUT.name() + currentInput + "L", FORECAST_CITY_INPUT.name() + currentInput + "Z",
                        FORECAST_CITY_INPUT.name() + currentInput + "X", FORECAST_CITY_INPUT.name() + currentInput + "C",
                        FORECAST_CITY_INPUT.name() + currentInput + "V", FORECAST_CITY_INPUT.name() + currentInput + "B",
                        FORECAST_CITY_INPUT.name() + currentInput + "N", FORECAST_CITY_INPUT.name() + currentInput + "M",
                        FORECAST_CITY_INPUT.name() + currentInput + "-", EMPTY.name(), EMPTY.name(), EMPTY.name(),
                        EMPTY.name(), EMPTY.name(), FORECAST_ENTERED_CITY.name() + currentInput,
                        currentInput.length() <= 1 ? FORECAST_CITY_INPUT.name() : FORECAST_CITY_INPUT.name()
                                + currentInput.substring(0, currentInput.length() - 1)
                )
        );
    }
}
