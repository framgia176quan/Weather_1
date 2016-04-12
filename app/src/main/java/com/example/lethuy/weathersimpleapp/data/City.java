package com.example.lethuy.weathersimpleapp.data;

import java.io.Serializable;

/**
 * Created by Nguyen Manh Q on 29/03/2016.
 */
public class City implements Serializable {

    private int id;
    private String name;
    private Coord coord;

    public City() {
    }

    public City(int id, String name, Coord coord) {
        this.id = id;
        this.name = name;
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
