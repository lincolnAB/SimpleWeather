package com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * Created by patrykormanin on 07/07/2017.
 */

public class HourlyWeatherEntity extends SugarRecord {
    public String getPlaceId() {
        return placeId;
    }

    public HourlyWeatherEntity() {
    }

    public HourlyWeatherEntity(String placeId, String temperatureData) {
        this.placeId = placeId;
        this.temperatureData = temperatureData;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTemperatureData() {
        return temperatureData;
    }

    public void setTemperatureData(String temperatureData) {
        this.temperatureData = temperatureData;
    }

    @Unique
    private String placeId;
    private String temperatureData;
}
