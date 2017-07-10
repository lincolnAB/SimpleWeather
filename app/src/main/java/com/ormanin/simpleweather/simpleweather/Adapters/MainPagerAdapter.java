package com.ormanin.simpleweather.simpleweather.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ormanin.simpleweather.simpleweather.MainPage.MainViewPagerFragment;
import com.ormanin.simpleweather.simpleweather.Model.Weather.CurrentWeatherPresenter;

import java.util.ArrayList;

/**
 * Created by patrykormanin on 26/06/2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CurrentWeatherPresenter> mData = new ArrayList<>();

    public ArrayList<CurrentWeatherPresenter> getData() {
        return mData;
    }

    public void setData(ArrayList<CurrentWeatherPresenter> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mData != null)
            return MainViewPagerFragment.newInstance(mData.get(position));
        else return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public CurrentWeatherPresenter getDataAt(int position) {
        if(mData.size() < position)
            return null;

        return mData.get(position);
    }
}
