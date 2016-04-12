package com.example.lethuy.weathersimpleapp.database;

import java.io.Serializable;

/**
 * Created by Nguyen Manh Quan on 07/04/2016.
 */
public class WeatherDatabase implements Serializable {

    private  int idWeather;
    private double id;
    private String temp;
    private String tempMin;
    private String tempMax;
    private String humidity;
    private  double pressure;
    private String description;
    private String icon;
    private String time;
    private String date;

    public WeatherDatabase() {
    }

    public WeatherDatabase(double id, String temp, String temp_min, String temp_max, String humidity, double pressure, String description, String icon, String time, String date) {

        this.id = id;
        this.temp = temp;
        this.tempMin = temp_min;
        this.tempMax = temp_max;
        this.humidity = humidity;
        this.pressure = pressure;
        this.description = description;
        this.icon = icon;
        this.time = time;
        this.date = date;
    }

    public int getIdWeather() {
        return idWeather;
    }

    public void setIdWeather(int id_weather) {
        this.idWeather = id_weather;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String temp_min) {
        this.tempMin = temp_min;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String temp_max) {
        this.tempMax = temp_max;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
