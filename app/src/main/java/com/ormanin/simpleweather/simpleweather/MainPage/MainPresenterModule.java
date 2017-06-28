package com.ormanin.simpleweather.simpleweather.MainPage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by patrykormanin on 23/06/2017.
 */

@Module
public class MainPresenterModule {

    private MainContract.View mView;

    public MainPresenterModule(MainContract.View mView) {
        this.mView = mView;
    }

    @Provides
    MainContract.View provideMainContractView() {
        return mView;
    }

}
