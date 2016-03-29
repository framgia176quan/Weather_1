package com.example.framgia.weathersimple.data;

/**
 * Created by framgia on 29/03/2016.
 */
public class CityObject {

        int id;
        String name;
        CoordObject coord;

    public CityObject() {
    }

    public CityObject(int id, String name, CoordObject coord) {
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

        public CoordObject getCoord() {
            return coord;
        }

        public void setCoord(CoordObject coord) {
            this.coord = coord;
        }
}
