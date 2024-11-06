package ru.sshibko.WeatherTeleBot.data;

public enum CommandData {

    start("start"),
    forecast("forecast"),
    help("help");

    public final String name;

    CommandData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
