package com.example.framgia.weathersimple.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Nguyen Manh Quan on 04/12/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments)  {
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
