package com.example.framgia.weathersimple.data;

import java.io.Serializable;

/**
 * Created by framgia on 29/03/2016.
 */
public class MainObject implements Serializable {
    double temp;
    double temp_min;
    double temp_max;
    double pressure;
    int  humidity;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return temp_min;
    }

    public void setTempMin(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTempMax() {
        return temp_max;
    }

    public void setTempMax(double temp_max) {
        this.temp_max = temp_max;
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
