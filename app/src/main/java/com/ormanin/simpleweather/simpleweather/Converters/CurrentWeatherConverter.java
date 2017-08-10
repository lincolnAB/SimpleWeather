package com.ormanin.simpleweather.simpleweather.Converters;

import com.ormanin.simpleweather.simpleweather.Model.CityModel.CityModel;
import com.ormanin.simpleweather.simpleweather.Model.POs.CurrentPager.CurrentWeatherPO;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.Currently;

/**
 * Created by patrykormanin on 07/07/2017.
 */

public class CurrentWeatherConverter {
    public static CurrentWeatherPO toCurrentWeatherPresenter(CityModel cityModel, Currently weatherModel) {
        CurrentWeatherPO convertedData = new CurrentWeatherPO();
        convertedData.setPlaceId(cityModel.getPlaceId());
        convertedData.setImageUrl(cityModel.getBackgroundUrl());
        convertedData.setCityName(cityModel.getCityName());
        convertedData.setTemperature(weatherModel.getTemperature().intValue());
        convertedData.setWeatherConditions(weatherModel.getSummary());
        convertedData.setWeatherIcon(WeatherIconsMapper.toWeatherIcon(weatherModel.getIcon()));
        return convertedData;
    }
}
