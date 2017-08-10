package com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart;

/**
 * Created by patrykormanin on 13/07/2017.
 */

public class TemperatureItemPO {

    public TemperatureItemPO(int temperature, int hour) {
        this.temperature = temperature;
        this.hour = hour;
    }

    private int temperature;
    private int hour;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
