package com.ormanin.simpleweather.simpleweather.MainPage;


import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ormanin.simpleweather.simpleweather.Callbacks.AsyncOperationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Callbacks.WeatherListCallback;
import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;
import com.ormanin.simpleweather.simpleweather.Converters.CurrentWeatherConverter;
import com.ormanin.simpleweather.simpleweather.Model.CityModel.CityModel;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PhotoService;
import com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart.HourlyWeatherEntity;
import com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart.HourlyWeatherPO;
import com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart.TemperatureItemPO;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.HourlyItem;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.Hourly;
import com.ormanin.simpleweather.simpleweather.Model.WeatherModel.WeatherModel;
import com.ormanin.simpleweather.simpleweather.Model.LocationModel.LocationModel;
import com.ormanin.simpleweather.simpleweather.Model.LocationModel.Result;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PlacesService;
import com.ormanin.simpleweather.simpleweather.Model.Networking.WeatherService;
import com.ormanin.simpleweather.simpleweather.Model.POs.CurrentPager.CurrentWeatherPO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private SharedPreferences mSharedPreferences;

    public MainPresenter(MainContract.View mainView, PlacesService placesService, WeatherService weatherService, PhotoService photoService, SharedPreferences sharedPreferences) {
        mMainView = mainView;
        mPlacesService = placesService;
        mWeatherService = weatherService;
        mPhotoService = photoService;
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshWeatherData() {
        mMainView.manageLoadingIndication(true);
        getAndSaveWeather(new WeatherListCallback() {
            @Override
            public void OnExecutionEnds(List<CurrentWeatherPO> weatherData) {
                mMainView.reloadWeatherAdapter(weatherData);
                mMainView.manageLoadingIndication(false);

                if (weatherData.size() == 0) {
                    mMainView.manageNoCitiesText(true);
                    mMainView.manageChartVisibility(false);
                }

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
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());
    }

    @Override
    public void deleteCity(String id) {
        CityModel.deleteAll(CityModel.class, "PLACE_ID = ?", id);
    }

    @Override
    public HourlyWeatherPO getHourlyWeatherById(String placeId) {
        List<HourlyWeatherEntity> hourlyWeatherData = find(HourlyWeatherEntity.class, "PLACE_ID = ?", placeId);

        if (hourlyWeatherData.size() == 0) return new HourlyWeatherPO();

        HourlyWeatherPO returnValue = new HourlyWeatherPO();
        returnValue.setPlaceId(hourlyWeatherData.get(0).getPlaceId());
        ArrayList<TemperatureItemPO> data = new Gson().fromJson(hourlyWeatherData.get(0).getTemperatureData(), new TypeToken<ArrayList<TemperatureItemPO>>() {
        }.getType());
        returnValue.setTemperatureData(data);

        return returnValue;
    }

    @Override
    public void saveSelectedIndex(int selectedIndex) {
        mSharedPreferences.edit().putInt("indexkey", selectedIndex).apply();
    }

    @Override
    public int getSavedSelectedIndex() {
        return mSharedPreferences.getInt("indexkey", 0);
    }

    @Override
    public String drawnNewBackgroundForCity(String placeId) {
        List<CityModel> cityQueryResult = CityModel.find(CityModel.class, "PLACE_ID = ?", placeId);
        String url = mPhotoService.getRandomPhotoUrl();
        if (cityQueryResult.size() > 0) {
            CityModel city = cityQueryResult.get(0);
            city.setBackgroundUrl(mPhotoService.getRandomPhotoUrl());
            city.save();
        }
        return url;
    }

    private void getAndSaveWeather(final WeatherListCallback response) {
        final ArrayList<CityModel> cities = new ArrayList<>(CityModel.listAll(CityModel.class));

        new AsyncTask<Void, Void, List<CurrentWeatherPO>>() {

            @Override
            protected List<CurrentWeatherPO> doInBackground(Void... voids) {
                //return value
                ArrayList<CurrentWeatherPO> weatherData = new ArrayList<>();

                for (int i = 0; i < cities.size(); i++) {
                    CityModel cityLocalData = cities.get(i);

                    try {
                        Response<WeatherModel> response = mWeatherService.getCurrentWeather("9777ad301bf4caf115bbdad2fc7a97d8", cityLocalData.getLatitude(), cityLocalData.getLongitude(), GlobalInfo.LANG_TYPE).execute();
                        CurrentWeatherPO data = CurrentWeatherConverter.toCurrentWeatherPresenter(cityLocalData, response.body().getCurrently());
                        saveHourlyWeather(data.getPlaceId(), response.body().getHourly());
                        weatherData.add(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return weatherData;
            }

            @Override
            protected void onPostExecute(List<CurrentWeatherPO> weatherPOJOS) {
                super.onPostExecute(weatherPOJOS);
                response.OnExecutionEnds(weatherPOJOS);
            }
        }.execute();

    }

    private void saveHourlyWeather(String placeId, Hourly data) {
        List<TemperatureItemPO> tempData = new ArrayList<>();
        for (HourlyItem item : data.getData()) {
            TemperatureItemPO itemPO = new TemperatureItemPO(item.getTemperature().intValue(), item.getTime() - GlobalInfo.REFERENCE_TIMESTAMP);
            tempData.add(itemPO);
        }
        HourlyWeatherEntity hourlyWeather = new HourlyWeatherEntity(placeId, new Gson().toJson(tempData));
        hourlyWeather.save();
    }
}
