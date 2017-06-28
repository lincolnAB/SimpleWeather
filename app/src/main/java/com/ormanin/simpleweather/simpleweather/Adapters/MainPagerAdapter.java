package com.ormanin.simpleweather.simpleweather.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ormanin.simpleweather.simpleweather.MainPage.MainViewPagerFragment;
import com.ormanin.simpleweather.simpleweather.Model.CityPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrykormanin on 26/06/2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CityPOJO> mData = new ArrayList<>();

    public ArrayList<CityPOJO> getData() {
        return mData;
    }

    public void setData(ArrayList<CityPOJO> mData) {
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
}
