package com.ormanin.simpleweather.simpleweather.Model.Networking;

import com.ormanin.simpleweather.simpleweather.MainApplication;
import com.ormanin.simpleweather.simpleweather.MainPage.AddCity.AddCityFragment;
import com.ormanin.simpleweather.simpleweather.MainPage.AppModule;
import com.ormanin.simpleweather.simpleweather.MainPage.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by patrykormanin on 29/06/2017.
 */

@Singleton
@Component(modules = {NetworkingModule.class, AppModule.class})

public interface NetworkingComponent {
    void inject(MainActivity activity);
    void inject(AddCityFragment fragment);
}
