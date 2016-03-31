package com.example.lethuy.weathersimpleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.lethuy.weathersimpleapp.data.model.City;
import com.example.lethuy.weathersimpleapp.data.model.Weather;

/**
 * Created by Le Thuy on 28/03/2016.
 */
public class Database extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = Database.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "WeatherManager";

    //Table Names
    private static final String TABLE_NAME_CITY = "city";
    private static final String TABLE_NAME_WEATHER = "weather";

    //Table Structure CITY
    public static final String CITY_ID = "id";
    public static final String CITY_NAME = "name";
    public static final String CITY_LON = "lon";
    public static final String CITY_LAT = "lat";
    public static final String CITY_COUNTRY = "country";

    //Table Structure WEATHER
    public static final String WEATHER_ID = "id";
    public static final String WEATHER_TEMP = "temp";
    public static final String WEATHER_TEMPMIN = "temp_min";
    public static final String WEATHER_TEMPMAX = "temp_max";
    public static final String WEATHER_PRESSURE = "pressure";
    public static final String WEATHER_HUMIDITY = "humidity";
    public static final String WEATHER_MAIN = "main";
    public static final String WEATHER_ICON = "icon";
    public static final String WEATHER_WINDALL = "all";
    public static final String WEATHER_SPEED = "speed";
    public static final String WEATHER_DEG = "deg";
    public static final String WEATHER_SEALEVEL = "sea_level";
    public static final String WEATHER_GROUNDLEVEL = "grnd_level";
    public static final String WEATHER_DATETIME = "dt_txt";
    public static final String WEATHER_DESCRIPTION = "description";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private SQLiteDatabase mdb;


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CITY = "CREATE TABLE" + TABLE_NAME_CITY
                + "(" + CITY_ID + " DOUBLE PRIMARY KEY, "
                + CITY_NAME + " TEXT, " + CITY_LON + " DOUBLE, "
                + CITY_LAT + " DOUBLE, " + CITY_COUNTRY + " TEXT)";
        db.execSQL(CREATE_TABLE_CITY);

        String CREATE_TABLE_WEATHER = "CREATE TABLE" + TABLE_NAME_WEATHER
                + "(" + WEATHER_ID + "DOUBLE PRIMARY KEY, "
                + WEATHER_TEMP + "DOUBLE,"
                + WEATHER_TEMPMIN + "DOUBLE, " + WEATHER_TEMPMAX + "DOUBLE, "
                + WEATHER_PRESSURE + "DOUBLE, "
                + WEATHER_HUMIDITY + "DOUBLE, " + WEATHER_MAIN + "TEXT, "
                + WEATHER_ICON + "TEXT, " + WEATHER_WINDALL + "DOUBLE, "
                + WEATHER_SPEED + "DOUBLE, " + WEATHER_DEG + "DOUBLE, "
                + WEATHER_SEALEVEL + "DOUBLE, " + WEATHER_GROUNDLEVEL + "DOUBLE, "
                + WEATHER_DATETIME + "DOUBLE, " + WEATHER_DESCRIPTION + "TEXT)";
        db.execSQL(CREATE_TABLE_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_CITY);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_WEATHER);
        onCreate(db);
    }

    /**
     * Creating a city
     */
    public double createCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITY_ID, city.getId());
        values.put(CITY_NAME, city.getName());
        values.put(CITY_LON, city.getLon());
        values.put(CITY_LAT, city.getLat());
        values.put(CITY_COUNTRY, city.getCountry());

        // insert row
        double city_id = db.insert(TABLE_NAME_CITY, null, values);

        return city_id;
    }


    /**
     * getting all city
     * @param name
     */
    public List<City> getAllCities(String name) {
        List<City> cityList = new ArrayList<City>();
        String sql = "SELECT * FROM " + TABLE_NAME_CITY;
        Log.e(LOG, sql);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mdb.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                City city = new City();
                city.setId(c.getDouble((c.getColumnIndex(CITY_ID))));
                city.setName(c.getString(c.getColumnIndex(CITY_NAME)));
                city.setLon(c.getDouble(c.getColumnIndex(CITY_LON)));
                city.setLat(c.getDouble(c.getColumnIndex(CITY_LAT)));
                city.setCountry(c.getString(c.getColumnIndex(CITY_COUNTRY)));

                cityList.add(city);
            } while (c.moveToNext());
        }
        return cityList;
    }

    /**
     * Creating a weather
     */
    public double createWeather(Weather weather) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WEATHER_ID, weather.getId());
        values.put(WEATHER_TEMP, weather.getTemp());
        values.put(WEATHER_TEMPMIN, weather.getTempMin());
        values.put(WEATHER_TEMPMAX, weather.getTempMax());
        values.put(WEATHER_PRESSURE, weather.getPressure());
        values.put(WEATHER_HUMIDITY, weather.getHumidity());
        values.put(WEATHER_MAIN, weather.getMain());
        values.put(WEATHER_ICON, weather.getTemp());
        values.put(WEATHER_WINDALL, weather.getCloudsAll());
        values.put(WEATHER_SPEED, weather.getWindSpeed());
        values.put(WEATHER_DEG, weather.getWindDeg());
        values.put(WEATHER_SEALEVEL, weather.getSeaLevel());
        values.put(WEATHER_GROUNDLEVEL, weather.getGroundLevel());
        values.put(WEATHER_DESCRIPTION, weather.getDescription());
        values.put(WEATHER_DATETIME, weather.getDateTime());

        // insert row
        double weather_id = db.insert(TABLE_NAME_WEATHER, null, values);

        return weather_id;
    }

    /**
     * getting all weather
     */

    public List<Weather> getAllWeather() {
        List<Weather> weatherList = new ArrayList<Weather>();
        String sql = "SELECT * FROM " + TABLE_NAME_WEATHER;
        Log.e(LOG, sql);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mdb.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                Weather weather = new Weather();
                weather.setId(c.getDouble((c.getColumnIndex(WEATHER_ID))));
                weather.setTemp(c.getDouble(c.getColumnIndex(WEATHER_TEMP)));
                weather.setTempMin(c.getDouble(c.getColumnIndex(WEATHER_TEMPMIN)));
                weather.setTempMax(c.getDouble(c.getColumnIndex(WEATHER_TEMPMAX)));
                weather.setHumidity(c.getDouble(c.getColumnIndex(WEATHER_HUMIDITY)));
                weather.setMain(c.getString(c.getColumnIndex(WEATHER_MAIN)));
                weather.setIcon(c.getString(c.getColumnIndex(WEATHER_ICON)));
                weather.setCloudsAll(c.getDouble(c.getColumnIndex(WEATHER_WINDALL)));
                weather.setWindSpeed(c.getDouble(c.getColumnIndex(WEATHER_SPEED)));
                weather.setWindDeg(c.getDouble(c.getColumnIndex(WEATHER_DEG)));
                weather.setSeaLevel(c.getDouble(c.getColumnIndex(WEATHER_SEALEVEL)));
                weather.setGroundLevel(c.getDouble(c.getColumnIndex(WEATHER_GROUNDLEVEL)));
                weather.setDateTime(c.getString(c.getColumnIndex(WEATHER_DATETIME)));
                weather.setDescription(c.getString(c.getColumnIndex(WEATHER_DESCRIPTION)));

                weatherList.add(weather);
            } while (c.moveToNext());
        }
        return weatherList;
    }

    /**
     * Updating a city
     */
    public int updateCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITY_NAME, city.getName());

        // updating row
        return db.update(TABLE_NAME_CITY, values, CITY_ID + " = ?",
                new String[]{String.valueOf(city.getId())});
    }

    /**
     * Updating a weather
     */
    public int updateWeather(Weather weather) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // updating row
        return db.update(TABLE_NAME_WEATHER, values, WEATHER_ID + " = ?",
                new String[]{String.valueOf(weather.getId())});
    }

    /**
     * Deleting a city
     */
    public void deleteCity(City city, boolean should_delete_all_city) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (should_delete_all_city) {
            List<City> allCities = getAllCities(city.getName());
        }
        db.delete(TABLE_NAME_CITY, CITY_ID + " = ?",
                new String[]{String.valueOf(city.getId())});
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
