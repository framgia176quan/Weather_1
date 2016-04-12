package com.example.lethuy.weathersimpleapp.database;

import java.io.Serializable;

/**
 * Created by Nguyen Manh Quan on 07/04/2016.
 */
public class CityDatabase implements Serializable {
    private double id;
    private String name;
    private double longitude;
    private double latitude;

    public CityDatabase() {
    }

    public CityDatabase(double id, String name, double longitude, double lat) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = lat;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
