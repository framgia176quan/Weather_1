package com.example.lethuy.weathersimpleapp.ui.activity;

import android.app.AlertDialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.data.WeatherData;
import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;
import com.example.lethuy.weathersimpleapp.ui.adapter.CityPagerAdapter;
import com.example.lethuy.weathersimpleapp.ui.adapter.WeatherWeekPagerAdapter;
import com.example.lethuy.weathersimpleapp.ui.fragment.CityTodayFragment;
import com.example.lethuy.weathersimpleapp.ui.fragment.WeatherWeekFragment;

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
