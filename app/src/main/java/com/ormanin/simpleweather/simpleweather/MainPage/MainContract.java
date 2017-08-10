package com.ormanin.simpleweather.simpleweather.MainPage;

import com.ormanin.simpleweather.simpleweather.BasePresenter;
import com.ormanin.simpleweather.simpleweather.BaseView;
import com.ormanin.simpleweather.simpleweather.Callbacks.AsyncOperationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Callbacks.ColorAnimationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Model.POs.CurrentPager.CurrentWeatherPO;
import com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart.HourlyWeatherPO;

import java.util.List;

/**
 * Created by patrykormanin on 23/06/2017.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void reloadBackground(String url, ColorAnimationEndedCallback callback);
        void showMenu();
        void openAddCity(android.view.View clickedView);
        void closeAddCity();
        void reloadWeatherAdapter(List<CurrentWeatherPO> data);
        void updateLastUpdateControl(String data);
        void manageNoCitiesText(boolean shouldBeVisible);
        void refreshBottomContainer(HourlyWeatherPO data, int chartColor);
        void manageLoadingIndication(boolean isReloading);
        void manageChartVisibility(boolean isVisible);
    }

    interface Presenter extends BasePresenter {
        void refreshWeatherData();
        void saveLocation(String placeId, AsyncOperationEndedCallback callback);
        String getFormattedCurrentTime();
        void deleteCity(String id);
        HourlyWeatherPO getHourlyWeatherById(String placeId);
        void saveSelectedIndex(int selectedIndex);
        int getSavedSelectedIndex();
        String drawnNewBackgroundForCity(String placeId);
    }

}
