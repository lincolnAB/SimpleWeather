package com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart;

import java.util.List;

/**
 * Created by patrykormanin on 12/07/2017.
 */

public class HourlyWeatherPO {
    private String placeId;
    private List<TemperatureItemPO> temperatureData;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<TemperatureItemPO> getTemperatureData() {
        return temperatureData;
    }

    public void setTemperatureData(List<TemperatureItemPO> temperatureData) {
        this.temperatureData = temperatureData;
    }

}

