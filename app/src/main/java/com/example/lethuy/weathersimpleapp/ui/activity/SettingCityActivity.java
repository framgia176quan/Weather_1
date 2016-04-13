package com.example.lethuy.weathersimpleapp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.data.City;
import com.example.lethuy.weathersimpleapp.data.Clouds;
import com.example.lethuy.weathersimpleapp.data.Coord;
import com.example.lethuy.weathersimpleapp.data.List;
import com.example.lethuy.weathersimpleapp.data.Main;
import com.example.lethuy.weathersimpleapp.data.Weather;
import com.example.lethuy.weathersimpleapp.data.WeatherData;
import com.example.lethuy.weathersimpleapp.data.Wind;
import com.example.lethuy.weathersimpleapp.database.CityDatabase;
import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;
import com.example.lethuy.weathersimpleapp.network.GetWeatherDataObject;
import com.example.lethuy.weathersimpleapp.network.ServiceHandler;
import com.example.lethuy.weathersimpleapp.ui.adapter.CityAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Nguyen Manh Quan on 11/04/2016.
 */
public class SettingCityActivity extends Activity {

    private Button btnAddCity, btnLocation;
    private EditText edtNameCity;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private CityAdapter cityAdapter;
    private ArrayList<CityDatabase> arrayList;

    private WeatherData weatherDataObject;

    private static String api = "http://api.openweathermap.org/data/2.5/forecast?appid=b7f3847208e4a3fe8913be845bdb88df&mode=json&q=";

    private static String urlImage = "http://openweathermap.org/img/w/";

    public static final String TAG_CITY = "city";
    public static final String TAG_CITY_ID = "id";
    public static final String TAG_CITY_NAME = "name";
    public static final String TAG_CITY_COORD = "coord";
    public static final String TAG_CITY_COORD_LON = "lon";
    public static final String TAG_CITY_COORD_LAT = "lat";

    public static final String TAG_LIST = "list";
    public static final String TAG_LIST_MAIN = "main";
    public static final String TAG_LIST_MAIN_TEMP = "temp";
    public static final String TAG_LIST_MAIN_TEMP_MIN = "temp_min";
    public static final String TAG_LIST_MAIN_TEMP_MAX = "temp_max";
    public static final String TAG_LIST_MAIN_PRESSURE = "pressure";
    public static final String TAG_LIST_MAIN_HUMIDITY = "humidity";


    public static final String TAG_LIST_WEATHER = "weather";

    public static final String TAG_LIST_WEATHER_ID = "id";
    public static final String TAG_LIST_WEATHER_MAIN = "main";
    public static final String TAG_LIST_WEATHER_DESCRRIPTION = "description";
    public static final String TAG_LIST_WEATHER_ICON = "icon";

    public static final String TAG_LIST_CLOUDS = "clouds";
    public static final String TAG_LIST_CLOUDS_ALL = "all";

    public static final String TAG_LIST_WIND = "wind";
    public static final String TAG_LIST_WIND_SPEED = "speed";
    public static final String TAG_LIST_WIND_DEG = "deg";

    public static final String TAG_LIST_DT_TXT = "dt_txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_city);
        initView();
        setupView();
    }

    public void initView(){
        db = new DatabaseHelper(SettingCityActivity.this);
        arrayList = db.getAllCity();
        cityAdapter = new CityAdapter(arrayList, SettingCityActivity.this);

        btnAddCity = (Button) findViewById(R.id.btn_add_city);
        edtNameCity = (EditText) findViewById(R.id.edt_name_city);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_city);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cityAdapter);
    }

    public void setupView(){
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNameCity.getText().toString().trim().length() == 0){
                    Toast.makeText(SettingCityActivity.this, "Bạn chưa nhập tên thành phố", Toast.LENGTH_SHORT).show();

                } else {
                    GetData getData = new GetData(SettingCityActivity.this.getApplicationContext());
                    getData.execute(edtNameCity.getText().toString());
                    edtNameCity.setText("");

                }
            }
        });
    }

    class GetData extends GetWeatherDataObject {


        public GetData(Context mContext) {
            super(mContext);
        }

        @Override
        protected JSONObject doInBackground(String... arg0) {

            ServiceHandler serviceHandler = new ServiceHandler();

            // Making a request to url and getting response
            String url = null;
            try {

                url = api + URLEncoder.encode(arg0[0], "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ;
            String jsonStr = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            JSONObject weatherJSONObject = new JSONObject();
//            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {

                try {
                    weatherJSONObject = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return weatherJSONObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                weatherDataObject = new WeatherData();
                City cityObject = new City();
                ArrayList<List> arrayListList = new ArrayList<List>();

                JSONObject city = result.getJSONObject(TAG_CITY);
                String id = city.getString(TAG_CITY_ID);
                String name = city.getString(TAG_CITY_NAME);

                JSONObject coord = city.getJSONObject(TAG_CITY_COORD);
                String lon = coord.getString(TAG_CITY_COORD_LON);
                String lat = coord.getString(TAG_CITY_COORD_LAT);

                cityObject.setId(Integer.parseInt(id));
                cityObject.setName(name);
                cityObject.setCoord(new Coord(Double.parseDouble(lon), Double.parseDouble(lat)));

                JSONArray list = result.getJSONArray(TAG_LIST);

                for (int i = 0; i < list.length(); i++) {
                    JSONObject obj = list.getJSONObject(i);
                    JSONObject main = obj.getJSONObject(TAG_LIST_MAIN);
                    String temp = main.getString(TAG_LIST_MAIN_TEMP);
                    String temp_min = main.getString(TAG_LIST_MAIN_TEMP_MIN);
                    String temp_max = main.getString(TAG_LIST_MAIN_TEMP_MAX);
                    String pressure = main.getString(TAG_LIST_MAIN_PRESSURE);
                    String humidity = main.getString(TAG_LIST_MAIN_HUMIDITY);

                    Main mainObject = new Main();
                    mainObject.setTemp(Double.parseDouble(temp));
                    mainObject.setTempMin(Double.parseDouble(temp_min));
                    mainObject.setTempMax(Double.parseDouble(temp_max));
                    mainObject.setPressure(Double.parseDouble(pressure));
                    mainObject.setHumidity(Integer.parseInt(humidity));

                    Log.d("Quan", "" + mainObject.toString());


                    JSONArray weatherArray = obj.getJSONArray(TAG_LIST_WEATHER);
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String idWeather = weather.getString(TAG_LIST_WEATHER_ID);
                    String mainWeather = weather.getString(TAG_LIST_WEATHER_MAIN);
                    String description = weather.getString(TAG_LIST_WEATHER_DESCRRIPTION);
                    String icon = weather.getString(TAG_LIST_WEATHER_ICON);

                    ArrayList<Weather> weatherObjects = new ArrayList<Weather>();
                    weatherObjects.add(0, new Weather(Integer.parseInt(idWeather), mainWeather, description, icon));

//                        Log.d("Quan", "" + weatherObjects.toString());

                    JSONObject clouds = obj.getJSONObject(TAG_LIST_CLOUDS);
                    String all = clouds.getString(TAG_LIST_CLOUDS_ALL);

                    Clouds cloudsObject = new Clouds();
                    cloudsObject.setAll(Integer.parseInt(all));

//                        Log.d("Quan", "" + cloudsObject.toString());

                    JSONObject wind = obj.getJSONObject(TAG_LIST_WIND);
                    String speed = wind.getString(TAG_LIST_WIND_SPEED);
                    String deg = wind.getString(TAG_LIST_WIND_DEG);

                    Wind windObject = new Wind();
                    windObject.setSpeed(Double.parseDouble(speed));
                    windObject.setDeg(Double.parseDouble(deg));

//                        Log.d("Quan", "" + windObject.toString());

                    String dt_txt = obj.getString(TAG_LIST_DT_TXT);
//                        Log.d("Quan", "" + dt_txt.toString());

                    arrayListList.add(new List(mainObject, weatherObjects, cloudsObject, windObject, dt_txt));

                }

                weatherDataObject.setCity(cityObject);
                weatherDataObject.setList(arrayListList);


            } catch (Exception e) {
                e.printStackTrace();
            }

            CityDatabase cityDatabase = new CityDatabase();
            cityDatabase.setId(weatherDataObject.getCity().getId());
            cityDatabase.setName(weatherDataObject.getCity().getName());
            cityDatabase.setLatitude(weatherDataObject.getCity().getCoord().getLattitude());
            cityDatabase.setLongitude(weatherDataObject.getCity().getCoord().getLongitude());
            db.addCity(cityDatabase);

            cityAdapter.addCity(cityDatabase);
            cityAdapter.notifyDataSetChanged();
            recyclerView.getAdapter().notifyDataSetChanged();

            Toast.makeText(SettingCityActivity.this, "Thêm thành phố thành công", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingCityActivity.this, WeatherActivity.class);
        startActivity(intent);
        finish();
    }
}
