package ru.sshibko.WeatherTeleBot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class Weather implements Serializable {

    private String icon;
    private String code;
    private String description;
}
