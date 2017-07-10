package com.ormanin.simpleweather.simpleweather.CustomComponents;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ormanin.simpleweather.simpleweather.Adapters.HourlyAdapter;

import java.util.ArrayList;

/**
 * Created by patrykormanin on 05/07/2017.
 */

public class HorizontalList extends LinearLayout {

    private HourlyAdapter mAdapter;

    public HorizontalList(Context context) {
        super(context);
    }

    public HorizontalList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HourlyAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(HourlyAdapter mAdapter) {
        this.mAdapter = mAdapter;
        refreshLayout();
    }

    private void refreshLayout() {
        if (this.getChildCount() == mAdapter.getCount()) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                mAdapter.getView(i, this.getChildAt(i), this);
            }
            return;
        }

        this.removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View v = mAdapter.getView(i, null, this);

            if (v != null)
                this.addView(v);
        }

    }
}

