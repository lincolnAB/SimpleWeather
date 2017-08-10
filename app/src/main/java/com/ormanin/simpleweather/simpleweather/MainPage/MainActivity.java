package com.ormanin.simpleweather.simpleweather.MainPage;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ormanin.simpleweather.simpleweather.Adapters.MainPagerAdapter;
import com.ormanin.simpleweather.simpleweather.Callbacks.AsyncOperationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Callbacks.ColorAnimationEndedCallback;
import com.ormanin.simpleweather.simpleweather.Helpers.BlurBuilder;
import com.ormanin.simpleweather.simpleweather.Helpers.GlobalInfo;
import com.ormanin.simpleweather.simpleweather.MainApplication;
import com.ormanin.simpleweather.simpleweather.MainPage.AddCity.AddCityFragment;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PhotoService;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PlacesService;
import com.ormanin.simpleweather.simpleweather.Model.Networking.WeatherService;
import com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart.TemperatureItemPO;
import com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel.Prediction;
import com.ormanin.simpleweather.simpleweather.Model.POs.CurrentPager.CurrentWeatherPO;
import com.ormanin.simpleweather.simpleweather.Model.POs.BottomChart.HourlyWeatherPO;
import com.ormanin.simpleweather.simpleweather.R;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View, AddCityFragment.OnListFragmentInteractionListener {

    MainPresenter mMainPresenter;

    //injected objects
    @Inject
    PlacesService mPlacesService;

    @Inject
    WeatherService mWeatherService;

    @Inject
    PhotoService mPhotoService;

    //Views
    private ImageView mBackgroundImageView;
    private View mBottomContainer;
    private ViewPager mPager;
    private ImageButton mButtonSettings;
    private LineChart mBottomTemperatureChart;
    private View mProgressBar;

    //Other
    private int mBottomContainerColor = Color.WHITE;
    private MainPagerAdapter mPagerAdapter;
    private PageIndicatorView mPageIndicator;
    private TextView mLastUpdateTextView;
    private View mNoCitiesTextView;

    //todo add attribution from SharedPreferences
    private int mSelectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View attributions
        mProgressBar = findViewById(R.id.loading_indicator);
        mPager = findViewById(R.id.view_pager);
        mPageIndicator = findViewById(R.id.page_indicator);
        mButtonSettings = findViewById(R.id.button_settings);
        mBackgroundImageView = findViewById(R.id.image_view);
        mBottomContainer = findViewById(R.id.bottom_container);
        mNoCitiesTextView = findViewById(R.id.text_view_no_cities);
        mLastUpdateTextView = findViewById(R.id.text_view_last_update);
        mBottomTemperatureChart = findViewById(R.id.line_chart_bottom);

        //required view initialization
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mMainPresenter.saveSelectedIndex(position);
                mSelectedIndex = position;
                reloadBackgroundAndChart(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Dagger injection
        ((MainApplication) getApplication()).getNetComponent().inject(this);
        mMainPresenter = new MainPresenter(this, mPlacesService, mWeatherService, mPhotoService, getSharedPreferences("test", MODE_PRIVATE));

        //view's data initialization
        mMainPresenter.refreshWeatherData();
        reloadBackground(GlobalInfo.START_BACKGROUND_URL, new ColorAnimationEndedCallback() {
            @Override
            public void AnimationHasBeenEnded(int color) {

            }
        });
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mMainPresenter = (MainPresenter) presenter;
    }

    @Override
    public void reloadBackground(String url, final ColorAnimationEndedCallback callback) {
        Glide.with(this)
                .load(url)
                .asBitmap()
                .crossFade()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Palette.generateAsync(Bitmap.createBitmap(resource, 0, resource.getHeight() / 7, resource.getWidth(), resource.getHeight() - (resource.getHeight() / 7)), new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                                final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), mBottomContainerColor, palette.getDarkMutedColor(Color.WHITE));
                                colorAnimation.setDuration(300);
                                colorAnimation.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        callback.AnimationHasBeenEnded((int) colorAnimation.getAnimatedValue());
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animator) {
                                        int newColor = (int) animator.getAnimatedValue();
                                        mBottomContainer.setBackgroundColor(newColor);
                                        mBottomContainerColor = newColor;
                                    }

                                });
                                colorAnimation.start();
                            }
                        });
                        return false;
                    }
                })
                .into(mBackgroundImageView);
    }

    @Override
    public void showMenu() {
        PopupMenu popupMenu = new PopupMenu(this, mButtonSettings);
        popupMenu.getMenuInflater().inflate(R.menu.main_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_change_graphics_item:
                        CurrentWeatherPO currentItem = mPagerAdapter.getDataAt(mSelectedIndex);
                        if(currentItem == null)
                            break;

                        manageChartVisibility(false);
                        reloadBackground(mMainPresenter.drawnNewBackgroundForCity(currentItem.getPlaceId()), new ColorAnimationEndedCallback() {
                            @Override
                            public void AnimationHasBeenEnded(int color) {
                                mMainPresenter.refreshWeatherData();
                            }
                        });
                        break;
                    case R.id.menu_refresh_item:
                        mMainPresenter.refreshWeatherData();
                        break;
                    case R.id.menu_delete_item:
                        if (mPagerAdapter.getCount() > 0) {
                            mMainPresenter.deleteCity(mPagerAdapter.getDataAt(mPager.getCurrentItem()).getPlaceId());
                            mMainPresenter.refreshWeatherData();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.info_delete_no_cities, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void reloadWeatherAdapter(List<CurrentWeatherPO> data) {
        if (data == null)
            return;

        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPagerAdapter.setData(new ArrayList<>(data));

        if (data.size() > 0)
            mPager.setCurrentItem(mMainPresenter.getSavedSelectedIndex());

        mPageIndicator.setViewPager(mPager);

        if(mPagerAdapter.getCount() > mSelectedIndex) {
            HourlyWeatherPO hourlyPres = mMainPresenter.getHourlyWeatherById(mPagerAdapter.getDataAt(mSelectedIndex).getPlaceId());
            refreshBottomContainer(hourlyPres, mBottomContainerColor);
        }

        updateLastUpdateControl(mMainPresenter.getFormattedCurrentTime());
    }

    @Override
    public void updateLastUpdateControl(final String data) {
        mLastUpdateTextView.animate().alpha(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mLastUpdateTextView.setText(getString(R.string.last_update) + data);
                mLastUpdateTextView.animate().alpha(1).setListener(null).start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void manageNoCitiesText(boolean shouldBeVisible) {
        if (shouldBeVisible) mNoCitiesTextView.setVisibility(View.VISIBLE);
        else mNoCitiesTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void refreshBottomContainer(HourlyWeatherPO data, int color) {

        List<Entry> values = new ArrayList<>();

        for (int i = 0; i < data.getTemperatureData().size(); i++) {
            if (i < 24 && i % 4 == 0) {
                TemperatureItemPO item = data.getTemperatureData().get(i);
                Entry entry = new Entry(item.getHour(), item.getTemperature(), "Label");
                values.add(entry);
            }
        }

        LineDataSet lineDataSet = new LineDataSet(values, "Label");
        lineDataSet.setColor(getBrighterColor(color, 1.3f));

        int chartPointColor = getBrighterColor(color, 0.8f);
        lineDataSet.setCircleColor(chartPointColor);
        lineDataSet.setCircleColorHole(chartPointColor);
        lineDataSet.setCircleRadius(5.0f);
        lineDataSet.setLineWidth(5.0f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setDrawValues(false);
        //lineDataSet.setValueFormatter(new TemperatureChartValueFormatter());

        LineData lineData = new LineData(lineDataSet);

        manageChartVisibility(true);
        mBottomTemperatureChart.getXAxis().setLabelCount(values.size(), true);
        mBottomTemperatureChart.setData(lineData);
        mBottomTemperatureChart.animateXY(1500, 1500, Easing.EasingOption.EaseInCubic, Easing.EasingOption.EaseInCubic);

    }

    @Override
    public void manageLoadingIndication(boolean isReloading) {
        if (isReloading) {
            mProgressBar.setVisibility(View.VISIBLE);
            mPager.setVisibility(View.INVISIBLE);
            mNoCitiesTextView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mPager.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListFragmentInteraction(Prediction item) {
        mMainPresenter.saveLocation(item.getPlaceId(), new AsyncOperationEndedCallback() {
            @Override
            public void OnOperationCompleted() {
                mMainPresenter.refreshWeatherData();
            }
        });
        closeAddCity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View imageView = this.findViewById(R.id.bgn_image_view);
        clearBackgroundWithAnim(imageView);
    }

    @Override
    public void openAddCity(View clickedView) {
        showBlurredBackground();

        AddCityFragment fragment = AddCityFragment.newInstance(1, mPlacesService);
        getSupportFragmentManager().beginTransaction().replace(R.id.popup_container, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("AddCity").commit();
    }

    @Override
    public void closeAddCity() {
        View imageView = this.findViewById(R.id.bgn_image_view);
        clearBackgroundWithAnim(imageView);
        getSupportFragmentManager().popBackStack();
        ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void showBlurredBackground() {
        View imageView = this.findViewById(R.id.bgn_image_view);
        imageView.setAlpha(0);

        Bitmap blurredImage = BlurBuilder.blur(this.findViewById(R.id.parent_container));
        imageView.setBackground(new BitmapDrawable(getResources(), blurredImage));
        imageView.animate().alpha(1).setListener(null).setDuration(500).start();
    }

    private void clearBackgroundWithAnim(final View view) {
        view.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setBackground(null);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();

    }

    private int getBrighterColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    @Override
    public void manageChartVisibility(boolean isVisible) {
        if (isVisible) mBottomTemperatureChart.setVisibility(View.VISIBLE);
        else mBottomTemperatureChart.setVisibility(View.INVISIBLE);
    }

    private void reloadBackgroundAndChart(int currentIndex) {
        if (mPagerAdapter.getCount() == 0)
            return;

        String url = mPagerAdapter.getDataAt(currentIndex).getImageUrl();

        //todo change impl
        manageChartVisibility(false);
        reloadBackground(url, new ColorAnimationEndedCallback() {
            @Override
            public void AnimationHasBeenEnded(int color) {
                HourlyWeatherPO hourlyPres = mMainPresenter.getHourlyWeatherById(mPagerAdapter.getDataAt(mSelectedIndex).getPlaceId());
                refreshBottomContainer(hourlyPres, color);
            }
        });
    }

}
