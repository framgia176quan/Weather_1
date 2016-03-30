package com.example.framgia.weathersimple.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framgia.weathersimple.R;
import com.example.framgia.weathersimple.data.CityObject;
import com.example.framgia.weathersimple.data.CloudsObject;
import com.example.framgia.weathersimple.data.CoordObject;
import com.example.framgia.weathersimple.data.ListObject;
import com.example.framgia.weathersimple.data.MainObject;
import com.example.framgia.weathersimple.data.WeatherDataObject;
import com.example.framgia.weathersimple.data.WeatherObject;
import com.example.framgia.weathersimple.data.WindObject;
import com.example.framgia.weathersimple.network.ServiceHandler;
import com.example.framgia.weathersimple.service.remote.GetWeatherDataObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CityFragment extends Fragment {

    TextView tvNameCity, tvDateTime, tvTemp, tvInfoWeather, tvTempNow, tvHumidityNow, tvWindNow;
    ImageView imgIcoWeather, imgIcoTempNow,imgIcoHumidityNow, imgIcoWindNow;
    ConnectivityManager connMgr;

    NetworkInfo networkInfo;
    WeatherDataObject weatherDataObject;
    String nameCity;

    private static String api = "http://api.openweathermap.org/data/2.5/forecast?appid=b7f3847208e4a3fe8913be845bdb88df&mode=json&q=";
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

    public CityFragment(String nameCity) {
        this.nameCity = nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public boolean isNetwork(){
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else  return false;

    }

    class GetData extends GetWeatherDataObject{


        public GetData(Context mContext) {
            super(mContext);
        }


        @Override
        protected JSONObject doInBackground(String... arg0) {

            ServiceHandler serviceHandler = new ServiceHandler();

            // Making a request to url and getting response
            String url = api + arg0;
            String jsonStr = serviceHandler.makeServiceCall(url.replaceAll(" ", "%20"), ServiceHandler.GET);
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
                weatherDataObject = new WeatherDataObject();
                CityObject cityObject = new CityObject();
                ArrayList<ListObject> arrayListList = new ArrayList<ListObject>();

                JSONObject city = result.getJSONObject(TAG_CITY);
                String id = city.getString(TAG_CITY_ID);
                String name = city.getString(TAG_CITY_NAME);

                JSONObject coord = city.getJSONObject(TAG_CITY_COORD);
                String lon = coord.getString(TAG_CITY_COORD_LON);
                String lat = coord.getString(TAG_CITY_COORD_LAT);

                cityObject.setId(Integer.parseInt(id));
                cityObject.setName(name);
                cityObject.setCoord(new CoordObject(Double.parseDouble(lon), Double.parseDouble(lat)));

                JSONArray list = result.getJSONArray(TAG_LIST);

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

                        Log.d("Quan", "" + mainObject.toString());


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

                weatherDataObject.setCity(cityObject);
                weatherDataObject.setList(arrayListList);


            } catch (Exception e) {
                e.printStackTrace();
            }

            bindDataToView(weatherDataObject);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container , false);


        initView(container);
        loadDataFromServer();
        return view;
    }

    public void initView(View view){
        LinearLayout llContent = (LinearLayout) view.findViewById(R.id.layout_content_detail_now);

        LinearLayout llWeather = (LinearLayout) view.findViewById(R.id.layout_detail_weather);

        tvNameCity = (TextView) view.findViewById(R.id.tv_name_city);
        Log.d("VA tag", "tvNameCity = " +tvNameCity );
        tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
        tvTemp = (TextView) view.findViewById(R.id.tv_temp);
        tvInfoWeather = (TextView) view.findViewById(R.id.tv_infor_weather);
        imgIcoWeather = (ImageView) view.findViewById(R.id.img_ico_weather);


        tvTempNow = (TextView) view.findViewById(R.id.tv_temp_now);
        tvHumidityNow = (TextView) view.findViewById(R.id.tv_humidity_now);
        tvWindNow = (TextView) view.findViewById(R.id.tv_wind_now);


        imgIcoTempNow = (ImageView) view.findViewById(R.id.img_ico_temp_now);
        imgIcoHumidityNow = (ImageView) view.findViewById(R.id.img_ico_humidity_now);
        imgIcoWindNow = (ImageView) view.findViewById(R.id.img_ico_wind_now);

    }

    public void bindDataToView(WeatherDataObject weatherDataObject){
        tvNameCity.setText("" + weatherDataObject.getCity().getName());

    }

    public void loadDataFromServer(){
        connMgr = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        if(isNetwork()){
            GetData getData = new GetData(getActivity());
            getData.execute(nameCity);

        }
    }
}
