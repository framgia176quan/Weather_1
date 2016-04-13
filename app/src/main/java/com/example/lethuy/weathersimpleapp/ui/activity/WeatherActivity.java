package com.example.lethuy.weathersimpleapp.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.data.City;
import com.example.lethuy.weathersimpleapp.data.Clouds;
import com.example.lethuy.weathersimpleapp.data.Coord;
import com.example.lethuy.weathersimpleapp.data.List;
import com.example.lethuy.weathersimpleapp.data.Main;
import com.example.lethuy.weathersimpleapp.data.Weather;
import com.example.lethuy.weathersimpleapp.data.WeatherData;
import com.example.lethuy.weathersimpleapp.data.Wind;
import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;
import com.example.lethuy.weathersimpleapp.network.GetWeatherDataObject;
import com.example.lethuy.weathersimpleapp.network.ServiceHandler;
import com.example.lethuy.weathersimpleapp.ui.adapter.CityPagerAdapter;
import com.example.lethuy.weathersimpleapp.ui.adapter.WeatherWeekPagerAdapter;
import com.example.lethuy.weathersimpleapp.ui.fragment.CityTodayFragment;
import com.example.lethuy.weathersimpleapp.ui.fragment.WeatherWeekFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by framgia on 12/04/2016.
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
//        setContentView(R.layout.activity_weather);

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
            if (isFinishing() ==false) {
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

            cityTodayFragment.setData(weatherDataObject);
            weatherWeekFragmentOne.setWeatherData(weatherDataObject, 8);
            weatherWeekFragmentTwo.setWeatherData(weatherDataObject, 24);

//            updateDataFromDatabase(weatherDataObject);


            Log.d("AVA", "" + cityTodayFragment.toString());
            Log.d("AVA", "" + weatherDataObject.getCity().getName().toString());

//            if (cityTodayFragment.getCityName().equalsIgnoreCase("ha noi")) {
//                if (SettingSharePreference.getIntPreference(WeatherActivity.this, SettingNotificationActivity.KEY_SETTING_NOTITICATION) == -1 || SettingSharePreference.getIntPreference(WeatherActivity.this, SettingNotificationActivity.KEY_SETTING_NOTITICATION) == 1) {
//                    ImageLoaderForNotification imageLoader = new ImageLoaderForNotification(WeatherActivity.this);
//                    imageLoader.execute("" + urlImage + weatherDataObject.getList().get(0).getWeather().get(0).getIcon() + ".png");
//                }
//            }

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

}
