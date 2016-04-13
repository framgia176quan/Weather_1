package com.example.lethuy.weathersimpleapp.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;
import com.example.lethuy.weathersimpleapp.ui.fragment.CityTodayFragment;

import java.util.ArrayList;

/**
 * Created by Nguyen Manh Quan on 4/4/2016.
 */
public class CityPagerAdapter extends FragmentPagerAdapter {

    private DatabaseHelper db;
    private ArrayList<CityTodayFragment> fragments;

    public CityPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<>();
        initFragments();
    }

    public CityPagerAdapter(FragmentManager fm, DatabaseHelper db) {
        super(fm);
        this.db = db;
        fragments = new ArrayList<>(

        );
        for (int i = 0; i < this.db.getAllCity().size(); i++) {
            CityTodayFragment cityTodayFragment = new CityTodayFragment();
            cityTodayFragment.setCityName(db.getAllCity().get(i).getName());
            fragments.add(cityTodayFragment);
        }
    }

    @Override
    public CityTodayFragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    private void initFragments() {

        CityTodayFragment cityHaNoi = new CityTodayFragment();
        cityHaNoi.setCityName("ha noi");


        fragments.add(cityHaNoi);

    }


    public ArrayList<String> getAllNamePager() {
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < fragments.size(); i++) {
            String nameCity = fragments.get(i).getCityName();
            strings.add(nameCity);
        }
        return strings;
    }
}
