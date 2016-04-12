package com.example.lethuy.weathersimpleapp.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nguyen Manh Quan on 28/03/2016.
 */
public class WeatherData implements Serializable {

    private City city;
    private ArrayList<List> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }
}
