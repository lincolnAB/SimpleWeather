package com.ormanin.simpleweather.simpleweather.MainPage;

import android.support.v4.view.ViewPager;

import com.ormanin.simpleweather.simpleweather.BasePresenter;
import com.ormanin.simpleweather.simpleweather.BaseView;
import com.ormanin.simpleweather.simpleweather.Callbacks.AsyncOperationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Model.Weather.CurrentWeatherPresenter;
import com.ormanin.simpleweather.simpleweather.Model.Weather.HourlyWeatherPresenter;

import java.util.List;

/**
 * Created by patrykormanin on 23/06/2017.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void reloadBackground(String url);
        void showMenu();
        void openAddCity(android.view.View clickedView);
        void closeAddCity();
        void reloadWeatherAdapter(List<CurrentWeatherPresenter> data);
        void updateLastUpdateControl(String data);
        void manageNoCitiesText(int collectionSize);
        void refreshBottomContainer(HourlyWeatherPresenter data);
        void manageLoadingIndication(boolean isReloading);
    }

    interface Presenter extends BasePresenter {
        void refreshWeatherData();
        void saveLocation(String placeId, AsyncOperationEndedCallback callback);
        String getFormattedCurrentTime();
        void deleteCity(String id);
        HourlyWeatherPresenter getHourlyWeatherById(String placeId);
    }

}
