package com.example.framgia.weathersimple.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by framgia on 28/03/2016.
 */
public class WeatherDataObject implements Serializable {

     CityObject city;

     ArrayList<ListObject> list;

    public CityObject getCity() {
        return city;
    }

    public void setCity(CityObject city) {
        this.city = city;
    }

    public ArrayList<ListObject> getList() {
        return list;
    }

    public void setList(ArrayList<ListObject> list) {
        this.list = list;
    }
}
