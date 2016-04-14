package com.example.lethuy.weathersimpleapp.ui.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.lethuy.weathersimpleapp.database.WeatherDatabase;

import com.example.lethuy.weathersimpleapp.network.GetWeatherDataObject;
import com.example.lethuy.weathersimpleapp.network.ServiceHandler;
import com.example.lethuy.weathersimpleapp.sharepreferences.SettingSharePreference;
import com.example.lethuy.weathersimpleapp.ui.adapter.CityPagerAdapter;
import com.example.lethuy.weathersimpleapp.ui.adapter.WeatherWeekPagerAdapter;
import com.example.lethuy.weathersimpleapp.ui.fragment.CityTodayFragment;
import com.example.lethuy.weathersimpleapp.ui.fragment.WeatherWeekFragment;
import com.example.lethuy.weathersimpleapp.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nguyen Manh Q on 12/04/2016.
 */
public class WeatherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPagerCity, viewPagerWeek;
    private CityPagerAdapter cityPagerAdapter;
    private WeatherWeekPagerAdapter weatherWeekPagerAdapter;
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
    private static final String TAG = "BroadcastTest";
    private Intent intent;
    private CityTodayFragment cityTodayFragment;
    private WeatherWeekFragment weatherWeekFragmentOne, weatherWeekFragmentTwo;
    private int timeCountDown;
    private AlertDialog.Builder alertDialog;
    final Handler myHandler = new Handler();
    private static final int secondTimeInOneMinute = 60000;
    private DatabaseHelper db;
    private int timesLoad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_weather);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        db = new DatabaseHelper(WeatherActivity.this);
        if (isNetworkOnline()) {
            db.deleteALLWeather();
        }
        overrideFonts(WeatherActivity.this, findViewById(R.id.main_view));
    }

    class GetData extends GetWeatherDataObject {
        private ProgressDialog pDialog;

        public GetData(Context mContext) {
            super(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isFinishing() == false) {
                pDialog = new ProgressDialog(WeatherActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
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
                String longtitude = coord.getString(TAG_CITY_COORD_LON);
                String lattitude = coord.getString(TAG_CITY_COORD_LAT);
                cityObject.setId(Integer.parseInt(id));
                cityObject.setName(name);
                cityObject.setCoord(new Coord(Double.parseDouble(longtitude), Double.parseDouble(lattitude)));
                JSONArray list = result.getJSONArray(TAG_LIST);
                for (int i = 0; i < list.length(); i++) {
                    JSONObject obj = list.getJSONObject(i);
                    JSONObject main = obj.getJSONObject(TAG_LIST_MAIN);
                    String temp = main.getString(TAG_LIST_MAIN_TEMP);
                    String tempMin = main.getString(TAG_LIST_MAIN_TEMP_MIN);
                    String tempMax = main.getString(TAG_LIST_MAIN_TEMP_MAX);
                    String pressure = main.getString(TAG_LIST_MAIN_PRESSURE);
                    String humidity = main.getString(TAG_LIST_MAIN_HUMIDITY);

                    Main mainObject = new Main();
                    mainObject.setTemp(Double.parseDouble(temp));
                    mainObject.setTempMin(Double.parseDouble(tempMin));
                    mainObject.setTempMax(Double.parseDouble(tempMax));
                    mainObject.setPressure(Double.parseDouble(pressure));
                    mainObject.setHumidity(Integer.parseInt(humidity));

                    JSONArray weatherArray = obj.getJSONArray(TAG_LIST_WEATHER);
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String idWeather = weather.getString(TAG_LIST_WEATHER_ID);
                    String mainWeather = weather.getString(TAG_LIST_WEATHER_MAIN);
                    String description = weather.getString(TAG_LIST_WEATHER_DESCRRIPTION);
                    String icon = weather.getString(TAG_LIST_WEATHER_ICON);

                    ArrayList<Weather> weatherObjects = new ArrayList<Weather>();
                    weatherObjects.add(0, new Weather(Integer.parseInt(idWeather), mainWeather, description, icon));
                    JSONObject clouds = obj.getJSONObject(TAG_LIST_CLOUDS);
                    String all = clouds.getString(TAG_LIST_CLOUDS_ALL);
                    Clouds cloudsObject = new Clouds();
                    cloudsObject.setAll(Integer.parseInt(all));
                    JSONObject wind = obj.getJSONObject(TAG_LIST_WIND);
                    String speed = wind.getString(TAG_LIST_WIND_SPEED);
                    String deg = wind.getString(TAG_LIST_WIND_DEG);
                    Wind windObject = new Wind();
                    windObject.setSpeed(Double.parseDouble(speed));
                    windObject.setDeg(Double.parseDouble(deg));
                    String dt_txt = obj.getString(TAG_LIST_DT_TXT);
                    arrayListList.add(new List(mainObject, weatherObjects, cloudsObject, windObject, dt_txt));
                }
                weatherDataObject.setCity(cityObject);
                weatherDataObject.setList(arrayListList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cityTodayFragment.setData(weatherDataObject);
            weatherWeekFragmentOne.setWeatherData(weatherDataObject, 8);
            weatherWeekFragmentTwo.setWeatherData(weatherDataObject, 24);
            updateDataFromDatabase(weatherDataObject);
            if (cityTodayFragment.getCityName().equalsIgnoreCase(getString(R.string.city_default))) {
                if (SettingSharePreference.getIntPreference(WeatherActivity.this, SettingNotificationActivity.KEY_SETTING_NOTITICATION) == -1 || SettingSharePreference.getIntPreference(WeatherActivity.this, SettingNotificationActivity.KEY_SETTING_NOTITICATION) == 1) {
                    ImageLoaderForNotification imageLoader = new ImageLoaderForNotification(WeatherActivity.this);
                    imageLoader.execute("" + urlImage + weatherDataObject.getList().get(0).getWeather().get(0).getIcon() + ".png");
                }
            }
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }


    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf"));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
// Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_city) {
            Intent intentSettingCity = new Intent(WeatherActivity.this, SettingCityActivity.class);
            startActivity(intentSettingCity);
            finish();
        } else if (id == R.id.nav_clock) {
            Intent intentSettingUpdate = new Intent(WeatherActivity.this, SettingUpdateActivity.class);
            startActivity(intentSettingUpdate);
            finish();
        } else if (id == R.id.nav_notification) {
            Intent intentSettingNotification = new Intent(WeatherActivity.this, SettingNotificationActivity.class);
            startActivity(intentSettingNotification);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void updateDataFromDatabase(WeatherData weatherDataObject) {

        db.addCity(new CityDatabase(weatherDataObject.getCity().getId(), weatherDataObject.getCity().getName(), weatherDataObject.getCity().getCoord().getLongitude(), weatherDataObject.getCity().getCoord().getLattitude()));

        db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(0).getMain().getHumidity() + "%"), weatherDataObject.getList().get(0).getMain().getPressure(), weatherDataObject.getList().get(0).getWeather().get(0).getMain(), weatherDataObject.getList().get(0).getWeather().get(0).getIcon(), weatherDataObject.getList().get(0).getDtTxt().substring(11, 19), weatherDataObject.getList().get(0).getDtTxt().substring(0, 10)));
        db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(8).getMain().getHumidity() + "%"), weatherDataObject.getList().get(8).getMain().getPressure(), weatherDataObject.getList().get(8).getWeather().get(0).getMain(), weatherDataObject.getList().get(8).getWeather().get(0).getIcon(), weatherDataObject.getList().get(8).getDtTxt().substring(11, 19), weatherDataObject.getList().get(8).getDtTxt().substring(0, 10)));
        db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(16).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(16).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(16).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(16).getMain().getHumidity() + "%"), weatherDataObject.getList().get(16).getMain().getPressure(), weatherDataObject.getList().get(16).getWeather().get(0).getMain(), weatherDataObject.getList().get(16).getWeather().get(0).getIcon(), weatherDataObject.getList().get(16).getDtTxt().substring(11, 19), weatherDataObject.getList().get(16).getDtTxt().substring(0, 10)));
        db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(24).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(24).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(24).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(24).getMain().getHumidity() + "%"), weatherDataObject.getList().get(24).getMain().getPressure(), weatherDataObject.getList().get(24).getWeather().get(0).getMain(), weatherDataObject.getList().get(24).getWeather().get(0).getIcon(), weatherDataObject.getList().get(24).getDtTxt().substring(11, 19), weatherDataObject.getList().get(24).getDtTxt().substring(0, 10)));
        db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(32).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(32).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(32).getMain().getHumidity() + "%"), weatherDataObject.getList().get(32).getMain().getPressure(), weatherDataObject.getList().get(32).getWeather().get(0).getMain(), weatherDataObject.getList().get(32).getWeather().get(0).getIcon(), weatherDataObject.getList().get(32).getDtTxt().substring(11, 19), weatherDataObject.getList().get(32).getDtTxt().substring(0, 10)));

    }

    private void updateGUI() {

        //tv.setText(String.valueOf(i));
        myHandler.post(updateRunable);
    }

    final Runnable updateRunable = new Runnable() {
        public void run() {

            processUpdate();
        }

    };//runnable


    public void processUpdate() {

        if (isNetworkOnline()) {
            if (timesLoad == 0) {
                viewPagerCity = (ViewPager) findViewById(R.id.viewpagerCity);
                viewPagerWeek = (ViewPager) findViewById(R.id.viewpagerWeek);


                if (db.getAllCity().size() <= 1) {

                    cityPagerAdapter = new CityPagerAdapter(getSupportFragmentManager());
                    viewPagerCity.setAdapter(cityPagerAdapter);

                } else {
                    cityPagerAdapter = new CityPagerAdapter(getSupportFragmentManager(), db);
                    viewPagerCity.setAdapter(cityPagerAdapter);
                }

                weatherWeekPagerAdapter = new WeatherWeekPagerAdapter(getSupportFragmentManager());
                viewPagerWeek.setAdapter(weatherWeekPagerAdapter);

                cityTodayFragment = cityPagerAdapter.getItem(0);
                weatherWeekFragmentOne = weatherWeekPagerAdapter.getItem(0);
                weatherWeekFragmentTwo = weatherWeekPagerAdapter.getItem(1);
                timesLoad++;
            }

            loadData(cityTodayFragment);

            viewPagerCity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    cityTodayFragment = cityPagerAdapter.getItem(position);
                    CityDatabase cityDatabaseChange = db.getCityByName(cityTodayFragment.getCityName());
                    viewPagerWeek.setCurrentItem(0);
                    weatherWeekFragmentOne = weatherWeekPagerAdapter.getItem(0);
                    weatherWeekFragmentTwo = weatherWeekPagerAdapter.getItem(1);

                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String strToday = dateFormat.format(date);

                    for (int i = 0; i < db.getAllWeatherInfo().size() - 1; i++) {

                        if (strToday.equalsIgnoreCase(db.getAllWeatherInfo().get(i).getDate().trim()) && db.getIdCity(cityPagerAdapter.getItem(position).getCityName()) == db.getAllWeatherInfo().get(i).getId()) {


                            cityTodayFragment.setDataOffline(cityDatabaseChange, db.getAllWeatherInfo().get(i));

                            weatherWeekFragmentOne.setDataOffline(db.getAllWeatherInfo().get(i + 1), db.getAllWeatherInfo().get(i + 2));
                            weatherWeekFragmentTwo.setDataOffline(db.getAllWeatherInfo().get(i + 3), db.getAllWeatherInfo().get(i + 4));
                            return;
                        }
                    }
                    cityTodayFragment = cityPagerAdapter.getItem(position);
                    viewPagerWeek.setCurrentItem(0);
                    weatherWeekFragmentOne = weatherWeekPagerAdapter.getItem(0);
                    weatherWeekFragmentTwo = weatherWeekPagerAdapter.getItem(1);
                    loadData(cityTodayFragment);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });


        } else {
            processUpdateOffline();
        }
    }

    public void processUpdateOffline() {

        viewPagerCity = (ViewPager) findViewById(R.id.viewpagerCity);
        viewPagerWeek = (ViewPager) findViewById(R.id.viewpagerWeek);

        if (db.getAllCity().size() <= 1) {

            cityPagerAdapter = new CityPagerAdapter(getSupportFragmentManager());
            viewPagerCity.setAdapter(cityPagerAdapter);

        } else {
            cityPagerAdapter = new CityPagerAdapter(getSupportFragmentManager(), db);
            viewPagerCity.setAdapter(cityPagerAdapter);
        }

        weatherWeekPagerAdapter = new WeatherWeekPagerAdapter(getSupportFragmentManager());
        viewPagerWeek.setAdapter(weatherWeekPagerAdapter);

        cityTodayFragment = cityPagerAdapter.getItem(0);
        weatherWeekFragmentOne = weatherWeekPagerAdapter.getItem(0);
        weatherWeekFragmentTwo = weatherWeekPagerAdapter.getItem(1);

        //TODO
        CityDatabase cityDatabase = new CityDatabase();
        cityDatabase = db.getCityByName(cityPagerAdapter.getItem(0).getCityName());

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strToday = dateFormat.format(date);
        for (int i = 0; i < db.getAllWeatherInfo().size() - 1; i++) {

            if (strToday.equalsIgnoreCase(db.getAllWeatherInfo().get(i).getDate().trim()) && db.getIdCity(cityPagerAdapter.getItem(0).getCityName()) == db.getAllWeatherInfo().get(i).getId()) {


                cityTodayFragment.setDataOffline(cityDatabase, db.getAllWeatherInfo().get(i));
                weatherWeekFragmentOne.setDataOffline(db.getAllWeatherInfo().get(i + 1), db.getAllWeatherInfo().get(i + 2));
                weatherWeekFragmentTwo.setDataOffline(db.getAllWeatherInfo().get(i + 3), db.getAllWeatherInfo().get(i + 4));
            }
        }

        viewPagerCity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                cityTodayFragment = cityPagerAdapter.getItem(position);
                CityDatabase cityDatabaseChange = db.getCityByName(cityTodayFragment.getCityName());
                Log.d("CHANGE", "" + cityDatabaseChange.getName());
                viewPagerWeek.setCurrentItem(0);
                weatherWeekFragmentOne = weatherWeekPagerAdapter.getItem(0);
                weatherWeekFragmentTwo = weatherWeekPagerAdapter.getItem(1);

                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strToday = dateFormat.format(date);

                for (int i = 0; i < db.getAllWeatherInfo().size() - 1; i++) {

                    if (strToday.equalsIgnoreCase(db.getAllWeatherInfo().get(i).getDate().trim()) && db.getIdCity(cityPagerAdapter.getItem(position).getCityName()) == db.getAllWeatherInfo().get(i).getId()) {


                        cityTodayFragment.setDataOffline(cityDatabaseChange, db.getAllWeatherInfo().get(i));

                        weatherWeekFragmentOne.setDataOffline(db.getAllWeatherInfo().get(i + 1), db.getAllWeatherInfo().get(i + 2));
                        weatherWeekFragmentTwo.setDataOffline(db.getAllWeatherInfo().get(i + 3), db.getAllWeatherInfo().get(i + 4));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private void loadData(CityTodayFragment cityTodayFragment) {


        if (isNetworkOnline()) {
            GetData getData = new GetData(WeatherActivity.this.getApplicationContext());
            getData.execute(cityTodayFragment.getCityName());
        } else {
            Toast.makeText(WeatherActivity.this, "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
            processUpdateOffline();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (isNetworkOnline()) {
            if (SettingSharePreference.getIntPreference(WeatherActivity.this, SettingUpdateActivity.KEY_SETTING_UPDATE) == -1 || SettingSharePreference.getIntPreference(WeatherActivity.this, SettingUpdateActivity.KEY_SETTING_UPDATE) == 0) {
                processUpdate();
            } else {
                timeCountDown = SettingSharePreference.getIntPreference(WeatherActivity.this, SettingUpdateActivity.KEY_SETTING_UPDATE);

                Timer myTimer = new Timer();
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        updateGUI();
                    }
                }, 0, secondTimeInOneMinute * timeCountDown);
            }
        } else {
            Toast.makeText(WeatherActivity.this, "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            processUpdate();
        }

    }

    private class ImageLoaderForNotification extends AsyncTask<String, Void, Bitmap> {

        Context ctx;
        String message;

        public ImageLoaderForNotification(Context context) {
            super();
            this.ctx = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            message = params[0];
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            super.onPostExecute(result);
            try {
                NotificationManager notificationManager = (NotificationManager) ctx
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                Intent intent = new Intent(ctx, WeatherActivity.class);
                intent.putExtra("isFromBadge", false);


                Notification notification = new Notification.Builder(ctx)
                        .setContentTitle("" + weatherDataObject.getCity().getName().toString())
                        .setContentText("" + weatherDataObject.getList().get(0).getWeather().get(0).getDescription() + " " + "" + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin())) + "\u2103" + " / " + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin())) + "\u2103")
                        .setSmallIcon(R.drawable.ic_weather)
                        .setLargeIcon(result)
                        .setStyle(new Notification.BigPictureStyle().bigPicture(result)).build();

                // hide the notification after its selected
                notification.flags |= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(1, notification);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
