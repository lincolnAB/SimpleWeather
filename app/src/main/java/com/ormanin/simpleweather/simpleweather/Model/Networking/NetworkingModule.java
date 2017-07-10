package com.ormanin.simpleweather.simpleweather.Model.Networking;

import android.app.Application;

import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by patrykormanin on 29/06/2017.
 */

@Module
public class NetworkingModule {

    public NetworkingModule() {
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    @Named("retrofitGoogleApi")
    Retrofit provideGoogleApiCall(Cache cache) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", 120))
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)
                .build();


        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("retrofitWeatherApi")
    Retrofit provideWeatherApiCall(Cache cache) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", 10))
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)
                .build();


        return new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public PlacesService provideGoogleApiService(
            @Named("retrofitGoogleApi") Retrofit retrofit) {
        return retrofit.create(PlacesService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public WeatherService provideWeatherApiService(
            @Named("retrofitWeatherApi") Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    @Singleton
    public PhotoService providePhotoService() {
        return new PhotoService(GlobalInfo.PHOTO_SERVICE_URL);
    }
}

