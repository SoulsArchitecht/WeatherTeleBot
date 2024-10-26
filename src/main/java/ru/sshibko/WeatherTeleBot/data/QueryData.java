package ru.sshibko.WeatherTeleBot.data;

public enum QueryData {
    MAIN ("main"),
    EMPTY("empty"),
    FORECAST("fc"),
    FORECAST_CITY_INPUT("fc_c_"),
    FORECAST_SEND_RESULT("fc_s_"),
    FORECAST_ENTERED_CITY("fc_e_");


    private final String name;

    QueryData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
