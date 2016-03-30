package com.example.framgia.weathersimple.data;

import java.io.Serializable;

/**
 * Created by framgia on 29/03/2016.
 */public class WindObject implements Serializable {
    double speed;
    double deg;

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