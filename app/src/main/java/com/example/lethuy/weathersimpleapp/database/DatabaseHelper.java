package com.example.lethuy.weathersimpleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Nguyen Manh Quan on 05/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weathersimple.sqlite";
    public static String DB_PATH;
    public SQLiteDatabase database;

    private static final String TABLE_CITY = "tblCity";
    private static final String KEY_ID_CITY = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LON = "lon";
    private static final String KEY_LAT = "lat";

    private static final String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "("
            + KEY_ID_CITY + " DOUBLE PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_LON + " DOUBLE,"
            + KEY_LAT + " DOUBLE)";

    private static final String TABLE_WEATHER = "tblWeather";
    private static final String KEY_ID_WEATHER = "id_weather";
    private static final String KEY_TEMP = "temp";
    private static final String KEY_TEMP_MIN = "temp_min";
    private static final String KEY_TEMP_MAX = "temp_max";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_PRESSURE = "pressure";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ICON = "icon";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";


    private static final String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
            + KEY_ID_WEATHER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ID_CITY + " DOUBLE,"
            + KEY_TEMP + " TEXT,"
            + KEY_TEMP_MIN + " TEXT,"
            + KEY_TEMP_MAX + " TEXT,"
            + KEY_HUMIDITY + " TEXT,"
            + KEY_PRESSURE + " DOUBLE,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_ICON + " TEXT,"
            + KEY_TIME + " TEXT,"
            + KEY_DATE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CITY_TABLE);
        db.execSQL(CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public CityDatabase getCityByName(String nameCity) {
        CityDatabase cityDatabase = new CityDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CITY + " where " + KEY_NAME+ " like " + "'%" + nameCity + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            do {

                cityDatabase.setId(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_ID_CITY))));
                cityDatabase.setName(cursor.getString(cursor
                        .getColumnIndex(KEY_NAME)));
                cityDatabase.setLatitude(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_LAT))));
                cityDatabase.setLongitude(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_LON))));
                // Adding contact to list
            } while (cursor.moveToNext());
        }
        return cityDatabase;
    }

    public double getIdCity(String nameCity) {

        CityDatabase cityDatabase = new CityDatabase();
        String selectQuery = "SELECT "+KEY_ID_CITY+" FROM " + TABLE_CITY + " where " + KEY_NAME+ " like " + "'%" + nameCity + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            do {
                cityDatabase.setId(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_ID_CITY))));
            } while (cursor.moveToNext());
        }
        return cityDatabase.getId();
    }

    public ArrayList<CityDatabase> getAllCity() {
        ArrayList<CityDatabase> listCity = new ArrayList<CityDatabase>();
        String selectQuery = "SELECT * FROM " + TABLE_CITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CityDatabase cityDatabase = new CityDatabase();
                cityDatabase.setId(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_ID_CITY))));
                cityDatabase.setName(cursor.getString(cursor
                        .getColumnIndex(KEY_NAME)));
                cityDatabase.setLatitude(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_LAT))));
                cityDatabase.setLongitude(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_LON))));
                // Adding contact to list
                listCity.add(cityDatabase);
            } while (cursor.moveToNext());
        }
        return listCity;
    }

    public ArrayList<WeatherDatabase> getAllWeatherInfo() {
        ArrayList<WeatherDatabase> list = new ArrayList<WeatherDatabase>();
        String selectQuery = "SELECT * FROM " + TABLE_WEATHER;
        Log.d("selectQuery", "" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                WeatherDatabase weatherDatabase = new WeatherDatabase();
                weatherDatabase.setIdWeather(Integer.parseInt(cursor.getString(cursor
                        .getColumnIndex(KEY_ID_WEATHER))));
                weatherDatabase.setId(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_ID_CITY))));
                weatherDatabase.setTemp(cursor.getString(cursor
                        .getColumnIndex(KEY_TEMP)));
                weatherDatabase.setTempMin(cursor.getString(cursor
                        .getColumnIndex(KEY_TEMP_MIN)));
                weatherDatabase.setTempMax(cursor.getString(cursor
                        .getColumnIndex(KEY_TEMP_MAX)));
                weatherDatabase.setHumidity(cursor.getString(cursor
                        .getColumnIndex(KEY_HUMIDITY)));
                weatherDatabase.setPressure(Double.parseDouble(cursor.getString(cursor
                        .getColumnIndex(KEY_PRESSURE))));
                weatherDatabase.setDescription(cursor.getString(cursor
                        .getColumnIndex(KEY_DESCRIPTION)));
                weatherDatabase.setIcon(cursor.getString(cursor
                        .getColumnIndex(KEY_ICON)));
                weatherDatabase.setTime(cursor.getString(cursor
                        .getColumnIndex(KEY_TIME)));
                weatherDatabase.setDate(cursor.getString(cursor
                        .getColumnIndex(KEY_DATE)));
                list.add(weatherDatabase);
            } while (cursor.moveToNext());
        }
        return list;
    }


    public void addCity(CityDatabase cityDatabase) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CITY, cityDatabase.getId());
        values.put(KEY_NAME, cityDatabase.getName());
        values.put(KEY_LAT, cityDatabase.getLatitude());
        values.put(KEY_LON, cityDatabase.getLongitude());
        // Inserting Row
        db.insert(TABLE_CITY, null, values);
        db.close(); // Closing database connection
    }

    public void addCityByName(String strNameCity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, strNameCity);
        db.insert(TABLE_CITY, null, values);
        db.close(); // Closing database connection
    }

    public void addWeather(WeatherDatabase weatherDatabase) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CITY, weatherDatabase.getId());
        values.put(KEY_TEMP, weatherDatabase.getTemp());
        values.put(KEY_TEMP_MIN, weatherDatabase.getTempMin());
        values.put(KEY_TEMP_MAX, weatherDatabase.getTempMax());
        values.put(KEY_HUMIDITY, weatherDatabase.getHumidity());
        values.put(KEY_PRESSURE, weatherDatabase.getPressure());
        values.put(KEY_DESCRIPTION, weatherDatabase.getDescription());
        values.put(KEY_ICON, weatherDatabase.getIcon());
        values.put(KEY_TIME, weatherDatabase.getTime());
        values.put(KEY_DATE, weatherDatabase.getDate());

        // Inserting Row
        db.insert(TABLE_WEATHER, null, values);
        db.close(); // Closing database connection
    }

    public void deleteCity(CityDatabase cityDatabase) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITY, KEY_NAME + " = ?",
                new String[] { String.valueOf(cityDatabase.getName()) });
        db.close();
    }

    public void deleteALLCity() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITY, null, null);
        db.close();
    }

    public void deleteALLWeather() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER, null, null);
        db.close();
    }
}

