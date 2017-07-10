package com.ormanin.simpleweather.simpleweather.Model.Weather;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

/**
 * Created by patrykormanin on 29/06/2017.
 */

public class CurrentWeatherPresenter extends SugarRecord {

    @Unique
    private String placeId;
    private String weatherConditions;
    private String cityName;
    private int Temperature;
    private String imageUrl;
    private String weatherIcon;

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getTemperature() {
        return Temperature;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public String getPlaceId() { return placeId; }

    public void setPlaceId(String placeId) { this.placeId = placeId; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getWeatherIcon() { return weatherIcon; }

    public void setWeatherIcon(String weatherIcon) { this.weatherIcon = weatherIcon; }
}
