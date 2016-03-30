package com.example.framgia.weathersimple.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by framgia on 29/03/2016.
 */
public class ListObject implements Serializable {

    MainObject main;
    ArrayList<WeatherObject> weather;
    CloudsObject clouds;
    WindObject wind;
    String dt_txt;

    public ListObject(MainObject main, ArrayList<WeatherObject> weather, CloudsObject clouds, WindObject wind, String dt_txt) {
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.dt_txt = dt_txt;
    }

    public MainObject getMain() {
        return main;
    }

    public void setMain(MainObject main) {
        this.main = main;
    }

    public ArrayList<WeatherObject> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherObject> weather) {
        this.weather = weather;
    }

    public CloudsObject getClouds() {
        return clouds;
    }

    public void setClouds(CloudsObject clouds) {
        this.clouds = clouds;
    }

    public WindObject getWind() {
        return wind;
    }

    public void setWind(WindObject wind) {
        this.wind = wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
