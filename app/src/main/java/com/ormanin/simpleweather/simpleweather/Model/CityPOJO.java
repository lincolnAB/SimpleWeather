package com.ormanin.simpleweather.simpleweather.Model;

/**
 * Created by patrykormanin on 27/06/2017.
 */

public class CityPOJO {
    private int temperature;
    private String cityName;
    private String weatherConditions;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
    }
}
