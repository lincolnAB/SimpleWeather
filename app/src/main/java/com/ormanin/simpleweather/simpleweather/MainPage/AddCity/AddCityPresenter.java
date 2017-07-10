package com.ormanin.simpleweather.simpleweather.MainPage.AddCity;

import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;
import com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel.SuggestionModel;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PlacesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by patrykormanin on 29/06/2017.
 */

public class AddCityPresenter implements AddCityContract.Presenter {

    private final AddCityContract.View mMainView;
    private final PlacesService mPlacesService;

    public AddCityPresenter(AddCityContract.View view, PlacesService service) {
        mMainView = view;
        mPlacesService = service;
    }

    @Override
    public void start() {

    }

    @Override
    public void getCitiesSuggestions(String cityPart, final Callback<SuggestionModel> cityCallback) {
        mPlacesService.getCitiesSuggestions("(cities)", cityPart, "AIzaSyD9ECwznSnQyJcO8ErADx2GtoxIf5zqJEU", GlobalInfo.LANG_TYPE).enqueue(new Callback<SuggestionModel>() {
            @Override
            public void onResponse(Call<SuggestionModel> call, Response<SuggestionModel> response) {
                //todo add proper returning value, not all object!!
                cityCallback.onResponse(call, response);
            }
            @Override
            public void onFailure(Call<SuggestionModel> call, Throwable t) {

            }
        });
    }
}
