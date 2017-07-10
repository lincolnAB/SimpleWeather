package com.ormanin.simpleweather.simpleweather;

import android.app.Application;

import com.orm.SugarApp;
import com.ormanin.simpleweather.simpleweather.MainPage.AppModule;
import com.ormanin.simpleweather.simpleweather.Model.Networking.DaggerNetworkingComponent;
import com.ormanin.simpleweather.simpleweather.Model.Networking.NetworkingComponent;
import com.ormanin.simpleweather.simpleweather.Model.Networking.NetworkingModule;

/**
 * Created by patrykormanin on 23/06/2017.
 */

public class MainApplication extends SugarApp {

    private NetworkingComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //Dagger modules initialization
        mNetComponent = DaggerNetworkingComponent.builder()
                .networkingModule(new NetworkingModule())
                .appModule(new AppModule(this))
                .build();
    }

    public NetworkingComponent getNetComponent() {
        return mNetComponent;
    }

}
