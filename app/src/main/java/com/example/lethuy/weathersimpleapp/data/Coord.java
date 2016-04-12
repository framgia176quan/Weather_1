package com.example.lethuy.weathersimpleapp.data;

import java.io.Serializable;

/**
 * Created by Nguyen Manh Quan on 29/03/2016.
 */
public class Coord implements Serializable {

    private double longitude;
    private double lattitude;

    public Coord(double lon, double lat) {
        this.longitude = lon;
        this.lattitude = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }
}
