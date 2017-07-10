package com.ormanin.simpleweather.simpleweather.Model.Weather;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * Created by patrykormanin on 07/07/2017.
 */

public class HourlyWeatherPresenter extends SugarRecord {
    public String getPlaceId() {
        return placeId;
    }

    public HourlyWeatherPresenter() {
    }

    public HourlyWeatherPresenter(String placeId, List<Double> temperatureData, String weatherIcon) {
        this.placeId = placeId;
        this.temperatureData = temperatureData;
        this.weatherIcon = weatherIcon;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<Double> getTemperatureData() {
        return temperatureData;
    }

    public void setTemperatureData(List<Double> temperatureData) {
        this.temperatureData = temperatureData;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    @Unique
    private String placeId;
    private List<Double> temperatureData;
    private String weatherIcon;
}
