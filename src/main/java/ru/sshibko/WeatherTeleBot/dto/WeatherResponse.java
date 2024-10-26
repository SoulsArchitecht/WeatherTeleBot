package ru.sshibko.WeatherTeleBot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class WeatherResponse implements Serializable {

    private List<WeatherData> weatherData;
}
