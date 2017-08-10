package com.ormanin.simpleweather.simpleweather.Callbacks;

import com.ormanin.simpleweather.simpleweather.Model.POs.CurrentPager.CurrentWeatherPO;

import java.util.List;

/**
 * Created by patrykormanin on 03/07/2017.
 */

public interface WeatherListCallback {
    void OnExecutionEnds(List<CurrentWeatherPO> weatherData);
}
