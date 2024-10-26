package ru.sshibko.WeatherTeleBot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sshibko.WeatherTeleBot.service.handler.CallbackQueryHandler;
import ru.sshibko.WeatherTeleBot.service.handler.CommandHandler;
import ru.sshibko.WeatherTeleBot.service.handler.MessageHandler;
import ru.sshibko.WeatherTeleBot.telegram.MyWeatherBot;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDispatcher {

    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;


    public BotApiMethod<?> distribute(Update update, MyWeatherBot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.charAt(0) == '/') {
                    return commandHandler.answer(update.getMessage(), bot);
                }
                return messageHandler.answer(update.getMessage(), bot);
            }
        }
        String warning = "Unsupported update type: " + update;
        log.warn(warning);
        //throw new UnsupportedOperationException(warning);
        return null;
    }
}
