package com.example.framgia.weathersimple.ui.activity;

import android.app.Dialog;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.weathersimple.R;
import com.example.framgia.weathersimple.data.CityObject;
import com.example.framgia.weathersimple.data.CloudsObject;
import com.example.framgia.weathersimple.data.CoordObject;
import com.example.framgia.weathersimple.data.ListObject;
import com.example.framgia.weathersimple.data.MainObject;
import com.example.framgia.weathersimple.data.WeatherObject;
import com.example.framgia.weathersimple.data.WeatherDataObject;
import com.example.framgia.weathersimple.data.WindObject;
import com.example.framgia.weathersimple.network.ServiceHandler;
import com.example.framgia.weathersimple.ui.adapter.PagerAdapter;
import com.example.framgia.weathersimple.ui.fragment.CityFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ProgressDialog pDialog;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;

    private static String api = "http://api.openweathermap.org/data/2.5/forecast?appid=b7f3847208e4a3fe8913be845bdb88df&mode=json&q=";

    public static final String KEY_SEND_DATA_CITY = "city";


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


    private WeatherDataObject weatherObjectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        viewPager = (ViewPager) findViewById(R.id.view_pager_main);


        if(isNetworkConnected()){
            GetWeatherDataObject getData = new GetWeatherDataObject();
            getData.execute();


        } else {
            Toast.makeText(MainActivity.this, "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
//            Dialog dialog = new Dialog(MainActivity.this);
//            dialog.setTitle(R.string.notice);
//            dialog.show();
        }

        initPaging();

    }



    public void initPaging(){

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(MainActivity.this, CityFragment.class.getName()));

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager_main);
        viewPager.setAdapter(pagerAdapter);
    }

    private class GetWeatherDataObject extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String url = api + "Ha noi";
            String jsonStr = sh.makeServiceCall(url.replaceAll(" ", "%20"), ServiceHandler.GET);

//            Log.d("Response: ", "> " + jsonStr);


            if (jsonStr != null) {
                try {
                    weatherObjectData  = new WeatherDataObject();
                    CityObject cityObject = new CityObject();
                    ArrayList<ListObject> arrayListList = new ArrayList<ListObject>();
                    JSONObject weatherJSONObject = new JSONObject(jsonStr);

                    JSONObject city = weatherJSONObject.getJSONObject(TAG_CITY);
                    String id = city.getString(TAG_CITY_ID);
                    String name = city.getString(TAG_CITY_NAME);

                    JSONObject coord = city.getJSONObject(TAG_CITY_COORD);
                    String lon = coord.getString(TAG_CITY_COORD_LON);
                    String lat = coord.getString(TAG_CITY_COORD_LAT);

                    cityObject.setId(Integer.parseInt(id));
                    cityObject.setName(name);
                    cityObject.setCoord(new CoordObject(Double.parseDouble(lon), Double.parseDouble(lat)));

                    JSONArray list = weatherJSONObject.getJSONArray(TAG_LIST);

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject obj = list.getJSONObject(i);
                        JSONObject main = obj.getJSONObject(TAG_LIST_MAIN);
                        String temp = main.getString(TAG_LIST_MAIN_TEMP);
                        String temp_min = main.getString(TAG_LIST_MAIN_TEMP_MIN);
                        String temp_max = main.getString(TAG_LIST_MAIN_TEMP_MAX);
                        String pressure = main.getString(TAG_LIST_MAIN_PRESSURE);
                        String humidity = main.getString(TAG_LIST_MAIN_HUMIDITY);

                        MainObject mainObject = new MainObject();
                        mainObject.setTemp(Double.parseDouble(temp));
                        mainObject.setTempMin(Double.parseDouble(temp_min));
                        mainObject.setTempMax(Double.parseDouble(temp_max));
                        mainObject.setPressure(Double.parseDouble(pressure));
                        mainObject.setHumidity(Integer.parseInt(humidity));

//                        Log.d("Quan", "" + mainObject.toString());


                        JSONArray weatherArray = obj.getJSONArray(TAG_LIST_WEATHER);
                        JSONObject weather = weatherArray.getJSONObject(0);
                        String idWeather = weather.getString(TAG_LIST_WEATHER_ID);
                        String mainWeather = weather.getString(TAG_LIST_WEATHER_MAIN);
                        String description = weather.getString(TAG_LIST_WEATHER_DESCRRIPTION);
                        String icon = weather.getString(TAG_LIST_WEATHER_ICON);

                        ArrayList<WeatherObject> weatherObjects = new ArrayList<WeatherObject>();
                        weatherObjects.add(0, new WeatherObject(Integer.parseInt(idWeather), mainWeather, description, icon));

//                        Log.d("Quan", "" + weatherObjects.toString());

                        JSONObject clouds = obj.getJSONObject(TAG_LIST_CLOUDS);
                        String all = clouds.getString(TAG_LIST_CLOUDS_ALL);

                        CloudsObject cloudsObject = new CloudsObject();
                        cloudsObject.setAll(Integer.parseInt(all));

//                        Log.d("Quan", "" + cloudsObject.toString());

                        JSONObject wind = obj.getJSONObject(TAG_LIST_WIND);
                        String speed = wind.getString(TAG_LIST_WIND_SPEED);
                        String deg = wind.getString(TAG_LIST_WIND_DEG);

                        WindObject windObject = new WindObject();
                        windObject.setSpeed(Double.parseDouble(speed));
                        windObject.setDeg(Double.parseDouble(deg));

//                        Log.d("Quan", "" + windObject.toString());

                        String dt_txt = obj.getString(TAG_LIST_DT_TXT);
//                        Log.d("Quan", "" + dt_txt.toString());

                        arrayListList.add(new ListObject(mainObject, weatherObjects, cloudsObject, windObject, dt_txt));

                    }

                    weatherObjectData.setCity(cityObject);
                    weatherObjectData.setList(arrayListList);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            Toast.makeText(MainActivity.this, ""+weatherObjectData.getCity().getName(), Toast.LENGTH_SHORT).show();
            Log.d("Quan quan", "" + weatherObjectData.toString());
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_SEND_DATA_CITY, weatherObjectData);
//            bundle.putSerializable(KEY_SEND_DATA_CITY, "hanoi");
            CityFragment cityFragment = new CityFragment();
            cityFragment.setArguments(bundle);

        }

    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
