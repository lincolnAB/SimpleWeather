package com.ormanin.simpleweather.simpleweather.MainPage;


import android.os.AsyncTask;

import com.ormanin.simpleweather.simpleweather.Callbacks.AsyncOperationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Callbacks.WeatherListCallback;
import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;
import com.ormanin.simpleweather.simpleweather.Converter.CurrentWeatherConverter;
import com.ormanin.simpleweather.simpleweather.Model.CityModel.CityModel;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PhotoService;
import com.ormanin.simpleweather.simpleweather.Model.Weather.HourlyWeatherPresenter;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.Datum;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.Hourly;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.WeatherModel;
import com.ormanin.simpleweather.simpleweather.Model.LocationModel.LocationModel;
import com.ormanin.simpleweather.simpleweather.Model.LocationModel.Result;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PlacesService;
import com.ormanin.simpleweather.simpleweather.Model.Networking.WeatherService;
import com.ormanin.simpleweather.simpleweather.Model.Weather.CurrentWeatherPresenter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.orm.SugarRecord.find;

/**
 * Created by patrykormanin on 23/06/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;
    private final PlacesService mPlacesService;
    private WeatherService mWeatherService;
    private PhotoService mPhotoService;

    public MainPresenter(MainContract.View mainView, PlacesService placesService, WeatherService weatherService, PhotoService photoService) {
        mMainView = mainView;
        mPlacesService = placesService;
        mWeatherService = weatherService;
        mPhotoService = photoService;
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshWeatherData() {
        mMainView.manageLoadingIndication(true);
        getAndSaveWeather(new WeatherListCallback() {
            @Override
            public void OnExecutionEnds(List<CurrentWeatherPresenter> weatherData) {
                mMainView.reloadWeatherAdapter(weatherData);
                mMainView.manageLoadingIndication(false);
                mMainView.manageNoCitiesText(weatherData.size());
            }
        });
    }

    @Override
    public void saveLocation(String placeId, final AsyncOperationEndedCallback callback) {
        mPlacesService.getLocationCoordinates(placeId, "AIzaSyD9ECwznSnQyJcO8ErADx2GtoxIf5zqJEU", GlobalInfo.LANG_TYPE).enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                //todo add proper returnning value, not whole object!!
                Result result = response.body().getResult();
                new CityModel(result.getFormattedAddress(), result.getPlaceId(), result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng(), mPhotoService.getRandomPhotoUrl())
                        .save();

                if (callback != null)
                    callback.OnOperationCompleted();
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {

            }
        });
    }

    @Override
    public String getFormattedCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("hh:mm").format(calendar.getTime());
    }

    @Override
    public void deleteCity(String id) {
        CityModel.deleteAll(CityModel.class, "PLACE_ID = ?", id);
    }

    @Override
    public HourlyWeatherPresenter getHourlyWeatherById(String placeId) {
        List<HourlyWeatherPresenter> hourlyWeatherData = find(HourlyWeatherPresenter.class, "PLACE_ID = ?", placeId);

        if (hourlyWeatherData.size() > 0) return hourlyWeatherData.get(1);
        else return null;
    }

    private void getAndSaveWeather(final WeatherListCallback response) {
        final ArrayList<CityModel> cities = new ArrayList<>(CityModel.listAll(CityModel.class));

        new AsyncTask<Void, Void, List<CurrentWeatherPresenter>>() {

            @Override
            protected List<CurrentWeatherPresenter> doInBackground(Void... voids) {
                //return value
                ArrayList<CurrentWeatherPresenter> weatherData = new ArrayList<>();

                for (int i = 0; i < cities.size(); i++) {
                    CityModel cityLocalData = cities.get(i);

                    try {
                        Response<WeatherModel> response = mWeatherService.getCurrentWeather("9777ad301bf4caf115bbdad2fc7a97d8", cityLocalData.getLatitude(), cityLocalData.getLongitude(), GlobalInfo.LANG_TYPE).execute();
                        CurrentWeatherPresenter data  = CurrentWeatherConverter.toCurrentWeatherPresenter(cityLocalData, response.body().getCurrently());
                        weatherData.add(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                return weatherData;
            }

            @Override
            protected void onPostExecute(List<CurrentWeatherPresenter> weatherPOJOS) {
                super.onPostExecute(weatherPOJOS);
                response.OnExecutionEnds(weatherPOJOS);
            }
        }.execute();

    }

    private void saveHourlyWeather(String placeId, Hourly data) {
        List<Double> tempData = new ArrayList<>();
        for (Datum item : data.getData()) {
            tempData.add(item.getTemperature());
        }

        HourlyWeatherPresenter hourlyWeather = new HourlyWeatherPresenter(placeId, tempData, data.getIcon());

        hourlyWeather.save();
    }
}
