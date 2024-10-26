package ru.sshibko.WeatherTeleBot.data;

public class TextConstant {

    public final static String BOT_START_INVITE =                         """
                                /start - начни взаимодействие с ботом
                                /help - перечень команд
                                /forecast - главная страница прогноза погоды
                                """;
    public final static String MAIN_INVITE =                         """
                                Это бот прогноза погоды.
                                Нажмите на нижнюю кнопку,
                                чтобы перейти на страницу
                                прогноза погоды.
                                """;

    public final static String INPUT_CITY_AND_VAR_INVITE =                         """
                                Пожалуйста, введите название города
                                на английском языке и выберите
                                один из двух вариантов прогноза
                                """;
    public final static String INPUT_VAR_INVITE =                         """
                                Выберите один из двух режимов:
                                Прогноз на следующие 24 часа
                                Прогноз на следующие 7 дней
                                """;

}
