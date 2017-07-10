package com.ormanin.simpleweather.simpleweather.Model.CityModel;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by patrykormanin on 30/06/2017.
 */

public class CityModel extends SugarRecord {

    @Unique
    private String placeId;
    private String cityName;
    private double latitude;
    private double longitude;
    private String backgroundUrl;

    public CityModel() {
    }

    public CityModel(String cityName, String placeId, double latitude, double longitude, String backgroundUrl) {
        this.cityName = cityName;
        this.placeId = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.backgroundUrl = backgroundUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBackgroundUrl() { return backgroundUrl; }

    public void setBackgroundUrl(String backgroundUrl) { this.backgroundUrl = backgroundUrl; }
}
