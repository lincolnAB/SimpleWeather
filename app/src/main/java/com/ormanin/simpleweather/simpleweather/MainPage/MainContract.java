package com.ormanin.simpleweather.simpleweather.MainPage;

import com.ormanin.simpleweather.simpleweather.BasePresenter;
import com.ormanin.simpleweather.simpleweather.BaseView;
import com.ormanin.simpleweather.simpleweather.Model.CityPOJO;

import java.util.List;

/**
 * Created by patrykormanin on 23/06/2017.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void reloadBackground(String url);
        void showMenu();
        void reloadWeatherAdapter(List<CityPOJO> data);
    }

    interface Presenter extends BasePresenter {
        void refreshWeatherData();
    }

}
