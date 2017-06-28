package com.ormanin.simpleweather.simpleweather.MainPage;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ormanin.simpleweather.simpleweather.Adapters.MainPagerAdapter;
import com.ormanin.simpleweather.simpleweather.MainPage.AddCity.AddCityFragment;
import com.ormanin.simpleweather.simpleweather.MainPage.AddCity.dummy.DummyContent;
import com.ormanin.simpleweather.simpleweather.Model.CityPOJO;
import com.ormanin.simpleweather.simpleweather.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View, AddCityFragment.OnListFragmentInteractionListener {

    @Inject
    MainPresenter mMainPresenter;

    //Views
    private ImageView mBackgroundImageView;
    private View mBottomContainer;
    private ViewPager mPager;
    private ImageButton mButtonSettings;

    //Other
    private int mBottomContainerColor = Color.WHITE;
    private MainPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View attributions
        mBackgroundImageView = (ImageView) this.findViewById(R.id.image_view);
        mBottomContainer = this.findViewById(R.id.bottom_container);
        mPager = (ViewPager) findViewById(R.id.view_pager);

        //local View attribution
        mButtonSettings = (ImageButton) findViewById(R.id.button_settings);

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
                //todo temporary
                switch (position) {
                    case 0:
                        reloadBackground("https://iso.500px.com/wp-content/uploads/2014/07/big-one.jpg");
                        break;
                    case 1:
                        reloadBackground("https://images.unsplash.com/39/wdXqHcTwSTmLuKOGz92L_Landscape.jpg");
                        break;
                    case 2:
                        reloadBackground("https://s-media-cache-ak0.pinimg.com/736x/d4/74/14/d4741465603f0e77c145d531a0bef8b4.jpg");
                        break;
                    case 3:
                        reloadBackground("https://www.photographytalk.com/images/articles/2017/04/28/iStock-545347988.jpg");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Presenter injection
        DaggerMainComponent.builder()
                .mainPresenterModule(new MainPresenterModule(this))
                .build()
                .inject(this);

        mMainPresenter.refreshWeatherData();
        reloadBackground("http://blog.agroknow.com/wp-content/uploads/2016/03/6835100-landscape.jpg");
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void reloadBackground(String url) {
        Glide.with(this)
                .load(url)
                .asBitmap()
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

                                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), mBottomContainerColor, palette.getDarkMutedColor(Color.WHITE));
                                colorAnimation.setDuration(300); // milliseconds
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
                    case R.id.menu_add_city_item:
                        mMainPresenter.refreshWeatherData();
                        showAddCity();
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public void reloadWeatherAdapter(List<CityPOJO> data) {
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setData(new ArrayList<>(data));

        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    private void showAddCity() {
        getSupportFragmentManager().beginTransaction().replace(R.id.popup_container, AddCityFragment.newInstance(1)).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("AddCity").commit();
    }
}
