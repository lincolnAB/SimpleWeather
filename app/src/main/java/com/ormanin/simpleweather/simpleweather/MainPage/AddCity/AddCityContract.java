package com.ormanin.simpleweather.simpleweather.MainPage.AddCity;

import com.ormanin.simpleweather.simpleweather.BasePresenter;
import com.ormanin.simpleweather.simpleweather.BaseView;
import com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel.SuggestionModel;

import retrofit2.Callback;

/**
 * Created by patrykormanin on 29/06/2017.
 */

public interface AddCityContract {

    interface View extends BaseView<Presenter> {
        void refreshRecycler(SuggestionModel data);
    }

    interface Presenter extends BasePresenter {
        void getCitiesSuggestions(String cityPart, Callback<SuggestionModel> callback);
    }

}
