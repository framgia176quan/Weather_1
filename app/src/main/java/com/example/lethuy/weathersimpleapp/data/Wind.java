package com.example.lethuy.weathersimpleapp.data;

import java.io.Serializable;

/**
 * Created by Nguyen Manh Quan on 29/03/2016.
 */public class Wind implements Serializable {

    private double speed;
    private double deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }
}