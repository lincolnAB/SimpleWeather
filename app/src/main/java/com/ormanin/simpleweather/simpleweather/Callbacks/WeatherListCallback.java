package com.ormanin.simpleweather.simpleweather.Callbacks;

import com.ormanin.simpleweather.simpleweather.Model.Weather.CurrentWeatherPresenter;

import java.util.List;

/**
 * Created by patrykormanin on 03/07/2017.
 */

public interface WeatherListCallback {
    void OnExecutionEnds(List<CurrentWeatherPresenter> weatherData);
}
