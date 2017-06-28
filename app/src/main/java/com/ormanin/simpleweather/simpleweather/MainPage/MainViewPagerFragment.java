package com.ormanin.simpleweather.simpleweather.MainPage;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ormanin.simpleweather.simpleweather.Model.CityPOJO;
import com.ormanin.simpleweather.simpleweather.R;

/**
 * Created by patrykormanin on 26/06/2017.
 */

public class MainViewPagerFragment extends Fragment {

    private CityPOJO mData;
    private TextView mTextViewTemperature;
    private TextView mTextViewWeatherDesctiption;
    private TextView mTextViewWeatherIcon;
    private TextView mTextViewPlaceDescription;

    public static Fragment newInstance(CityPOJO data) {
        MainViewPagerFragment fragment = new MainViewPagerFragment();
        fragment.mData = data;

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_viewpager_main, container, false);

        mTextViewTemperature = rootView.findViewById(R.id.text_view_temperature);
        mTextViewWeatherIcon = rootView.findViewById(R.id.text_view_weather_icon);
        mTextViewPlaceDescription = rootView.findViewById(R.id.text_view_place_description);
        mTextViewWeatherDesctiption = rootView.findViewById(R.id.text_view_weather_description);

        if (mData != null) {
            mTextViewTemperature.setText((Integer.toString(mData.getTemperature())));
            mTextViewPlaceDescription.setText(mData.getCityName());
            mTextViewWeatherDesctiption.setText(mData.getWeatherConditions());
        }

        return rootView;
    }
}
