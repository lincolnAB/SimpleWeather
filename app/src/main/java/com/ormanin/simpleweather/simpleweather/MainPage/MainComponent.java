package com.ormanin.simpleweather.simpleweather.MainPage;

import dagger.Component;

/**
 * Created by patrykormanin on 23/06/2017.
 */

@Component(modules = MainPresenterModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

}
