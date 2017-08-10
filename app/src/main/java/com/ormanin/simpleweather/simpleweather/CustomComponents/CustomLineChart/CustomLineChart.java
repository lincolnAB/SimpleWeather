package com.ormanin.simpleweather.simpleweather.CustomComponents.CustomLineChart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;

/**
 * Created by patrykormanin on 11/07/2017.
 */

public class CustomLineChart extends LineChart {
    public CustomLineChart(Context context) {
        super(context);
        initialize();
    }

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CustomLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        this.setDescription(null);
        this.getAxisRight().setEnabled(false);
        this.getLegend().setEnabled(false);
        this.setScaleEnabled(false);

        getAxisLeft().setDrawAxisLine(false);
        getAxisLeft().setTextColor(Color.WHITE);
        getAxisLeft().setTextSize(12f);
        getAxisLeft().setXOffset(10f);

        this.setExtraBottomOffset(20f);

        this.setHighlightPerTapEnabled(false);
        this.setHighlightPerDragEnabled(false);

        this.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.getXAxis().setDrawGridLines(false);
        this.getXAxis().setDrawAxisLine(false);
        this.getXAxis().setGranularity(1f);
        this.getXAxis().setTextColor(Color.WHITE);
        this.getXAxis().setTextSize(14f);
        this.getXAxis().setValueFormatter(new ChartValueConverter());

    }
}
