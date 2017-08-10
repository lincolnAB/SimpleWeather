package com.ormanin.simpleweather.simpleweather.Converters;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by patrykormanin on 13/07/2017.
 */

public class TemperatureChartValueFormatter implements IValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return ((int) value) + "° C";
    }
}
