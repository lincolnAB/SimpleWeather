package com.ormanin.simpleweather.simpleweather.Model.Networking;

import com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel.SuggestionModel;
import com.ormanin.simpleweather.simpleweather.Model.LocationModel.LocationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by patrykormanin on 29/06/2017.
 */

public interface PlacesService {
    @GET("place/autocomplete/json?types=(cities)")
    Call<SuggestionModel> getCitiesSuggestions(@Query("types") String types, @Query("input") String searchString, @Query("key") String apiKey, @Query("language") String languageCode);

    @GET("place/details/json")
    Call<LocationModel> getLocationCoordinates(@Query("placeid") String placeId, @Query("key") String apiKey, @Query("language") String languageCode);
}
