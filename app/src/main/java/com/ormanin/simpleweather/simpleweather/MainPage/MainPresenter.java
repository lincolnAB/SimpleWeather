package com.ormanin.simpleweather.simpleweather.MainPage;

import com.ormanin.simpleweather.simpleweather.Model.CityPOJO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * Created by patrykormanin on 23/06/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;

    @Inject
    public MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshWeatherData() {
        mMainView.reloadWeatherAdapter(getTestData());
    }

    private List<CityPOJO> getTestData() {
        ArrayList<CityPOJO> data = new ArrayList<>();

        final Random rand = new Random();

        CityPOJO city = new CityPOJO() {
            {
                setCityName("Buenos Aires, Brazil");
                setTemperature(rand.nextInt(33));
                setWeatherConditions("Clear");
            }
        };

        data.add(city);

        city  = new CityPOJO() {
            {
                setCityName("London, United Kingdom");
                setTemperature(rand.nextInt(33));
                setWeatherConditions("Rainy");
            }
        };

        data.add(city);

        city = new CityPOJO() {
            {
                setCityName("Buenos Aires, Brazil");
                setTemperature(rand.nextInt(33));
                setWeatherConditions("Partly Cloudy");
            }
        };

        data.add(city);

        city = new CityPOJO() {
            {
                setCityName("Vancouver, Canada");
                setTemperature(rand.nextInt(33));
                setWeatherConditions("Sunny");
            }
        };

        data.add(city);

        return data;
    }
}
