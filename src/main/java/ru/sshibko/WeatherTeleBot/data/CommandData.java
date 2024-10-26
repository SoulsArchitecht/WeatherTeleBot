package ru.sshibko.WeatherTeleBot.data;

public enum CommandData {

    START("start"),
    FORECAST("forecast"),
    HELP("help");

    public final String name;

    CommandData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
