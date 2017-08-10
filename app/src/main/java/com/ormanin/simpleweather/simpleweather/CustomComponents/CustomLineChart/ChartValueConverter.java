package com.ormanin.simpleweather.simpleweather.CustomComponents.CustomLineChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ormanin.simpleweather.simpleweather.Helpers.EpochConverter;
import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;

/**
 * Created by patrykormanin on 12/07/2017.
 */

public class ChartValueConverter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return EpochConverter.toHH((int) value);
    }
}
