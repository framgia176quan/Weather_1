package com.example.lethuy.weathersimpleapp.ui.activity;

import android.app.Application;

import com.example.lethuy.weathersimpleapp.util.TypefaceUtil;


/**
 * uan on 08/04/2016.
 */

public class AppWeatherSimple extends Application {
    @Override
    public void onCreate() {
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Roboto-Bold.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}