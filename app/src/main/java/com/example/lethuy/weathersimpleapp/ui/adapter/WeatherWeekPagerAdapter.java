package com.example.lethuy.weathersimpleapp.ui.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lethuy.weathersimpleapp.ui.fragment.WeatherWeekFragment;

import java.util.ArrayList;

/**
 * Created by Nguyen Manh Quan on 4/4/2016.
 */
public class WeatherWeekPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<WeatherWeekFragment> fragments;

    public WeatherWeekPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        initFragment();
    }

    @Override
    public WeatherWeekFragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    private void initFragment() {
        for (int i = 0; i < 2; i++) {
            fragments.add(new WeatherWeekFragment());
        }
    }


}
