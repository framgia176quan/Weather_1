package com.example.lethuy.weathersimpleapp.data;

import java.io.Serializable;

/**
 * Created by Nguyen Manh Quan on 29/03/2016.
 */
public class Main implements Serializable {

    private double temp;
    private double tempMin;
    private double tempMax;
    private double pressure;
    private int  humidity;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double temp_min) {
        this.tempMin = temp_min;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double temp_max) {
        this.tempMax = temp_max;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

}
