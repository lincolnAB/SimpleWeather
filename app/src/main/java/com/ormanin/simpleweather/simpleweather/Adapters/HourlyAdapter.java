package com.ormanin.simpleweather.simpleweather.Adapters;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.ormanin.simpleweather.simpleweather.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by patrykormanin on 05/07/2017.
 */

public class HourlyAdapter implements Adapter {

    private List<Double> mData;

    public HourlyAdapter(List<Double> mData) {
        this.mData = mData;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View createdView;

        if (view != null) createdView = view;
        else createdView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hourly_weather, viewGroup, false);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, i);
        String time = new SimpleDateFormat("HH").format(calendar.getTime());
        ((TextView) createdView.findViewById(R.id.text_view_hour)).setText(time);

        if(mData.size() > i) {
            ((TextView) createdView.findViewById(R.id.text_view_weather_icon)).setText("T");
            ((TextView) createdView.findViewById(R.id.text_view_temperature)).setText(Double.toString(mData.get(i)));
        }
        return createdView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
