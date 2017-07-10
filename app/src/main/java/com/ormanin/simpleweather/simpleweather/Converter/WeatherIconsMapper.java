package com.ormanin.simpleweather.simpleweather.Converter;

/**
 * Created by patrykormanin on 10/07/2017.
 */

public class WeatherIconsMapper {
    public static String toWeatherIcon(String weatherIcon) {
        switch (weatherIcon) {
            case "clear-day":
                return "B";
            case "clear-night":
                return "C";
            case "rain":
                return "R";
            case "snow":
                return "W";
            case "sleet":
                return "X";
            case "wind":
                return "F";
            case "fog":
                return "M";
            case "cloudy":
                return "Y";
            case "partly-cloudy-day":
                return "H";
            case "partly-cloudy-night":
                return "I";
            default:
                return "A";
        }
    }
}
