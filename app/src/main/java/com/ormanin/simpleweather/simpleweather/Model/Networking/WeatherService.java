package com.ormanin.simpleweather.simpleweather.Model.Networking;

import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by patrykormanin on 03/07/2017.
 */

public interface WeatherService {

    //todo (localization) add language as parameter
    @GET("{apiKey}/{latitude},{longitude}?units=si&exclude=minutely,alerts,flags")
    Call<WeatherModel> getCurrentWeather(@Path("apiKey") String apiKey, @Path("latitude") double latitude, @Path("longitude") double longitude, @Query("lang") String langCode);
}
