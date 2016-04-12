package com.example.lethuy.weathersimpleapp.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nguyen Manh Quan on 29/03/2016.
 */
public class List implements Serializable {

    private Main main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private String dtTxt;

    public List(Main main, ArrayList<Weather> weather, Clouds clouds, Wind wind, String dtTxt) {
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.dtTxt = dtTxt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
