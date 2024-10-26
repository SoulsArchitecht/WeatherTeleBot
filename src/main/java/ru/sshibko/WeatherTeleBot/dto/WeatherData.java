package ru.sshibko.WeatherTeleBot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class WeatherData implements Serializable {

    private double windSpeed;
    private int temperature;
    private int pop;
    private Weather weather;
}
