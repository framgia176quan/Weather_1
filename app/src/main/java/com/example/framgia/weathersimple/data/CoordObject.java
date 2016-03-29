package com.example.framgia.weathersimple.data;

/**
 * Created by framgia on 29/03/2016.
 */
public class CoordObject {
    double lon;
    double lat;

    public CoordObject(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
