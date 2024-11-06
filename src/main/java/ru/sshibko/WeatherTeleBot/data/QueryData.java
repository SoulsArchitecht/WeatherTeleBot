package ru.sshibko.WeatherTeleBot.data;

/** for next values: MAIN, EMPTY, FORECAST, FORECAST_CITY_INPUT, FORECAST_SEND_RESULT, FORECAST_ENTERED_CITY */
public enum QueryData {
    main("main"),
    empty("empty"),
    fc("fc"),
    fc_c_("fc_c_"),
    fc_s_("fc_s_"),
    fc_e_("fc_e_");


    private final String name;

    QueryData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
