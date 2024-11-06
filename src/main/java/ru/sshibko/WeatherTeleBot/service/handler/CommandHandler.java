package ru.sshibko.WeatherTeleBot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sshibko.WeatherTeleBot.data.CommandData;
import ru.sshibko.WeatherTeleBot.service.manager.ForecastManager;
import ru.sshibko.WeatherTeleBot.service.manager.MainManager;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

import static ru.sshibko.WeatherTeleBot.data.TextConstant.BOT_START_INVITE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final MainManager mainManager;

    private final ForecastManager forecastManager;

    public BotApiMethod<?> answer(Message message, MyWeatherBot bot) {

        String command = message.getText().substring(1);
        CommandData commandData;
        try {
            commandData = CommandData.valueOf(command);
        } catch (Exception e) {
            log.warn("Unsupported command was received: " + command);
            return unknownCommand(message.getChatId(), command);
        }

        switch (commandData) {
            case start -> {
                return mainManager.answerCommand(message, bot);
            }
            case help -> {
                return showCommandList(message.getChatId());
            }
            case forecast -> {
                return forecastManager.answerCommand(message, bot);
            }
        }

        throw new UnsupportedOperationException();
    }

    private BotApiMethod<?> showCommandList(Long chatId) {

        return SendMessage.builder()
                .chatId(chatId)
                .text(BOT_START_INVITE)
                .build();
    }

    private BotApiMethod<?> unknownCommand (Long chatId, String command) {

        return SendMessage.builder()
                .text("Я не знаю команды " + command +
                        "\n\n Смотрите перечень команд по ссылке help: \n /help")
                .chatId(chatId)
                .build();
    }
}
