package com.example.lethuy.weathersimpleapp.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.data.WeatherData;
import com.example.lethuy.weathersimpleapp.database.CityDatabase;
import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;
import com.example.lethuy.weathersimpleapp.database.WeatherDatabase;
import com.example.lethuy.weathersimpleapp.network.ImageDownloader;
import com.example.lethuy.weathersimpleapp.util.Tool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nguyen Manh Quan on 4/4/2016.
 */
public class CityTodayFragment extends Fragment {
    private TextView tvNameCity, tvDateTime, tvTemp, tvInfoWeather, tvTempNow, tvHumidityNow, tvPressureNow;
    private ImageView imgIcoWeather;
    private Calendar calendar;
    private String strDayOfWeek;
    private String strMonthOfYear;
    private String strDayOfMonth;
    private String strHour;
    private String strMinute;
    private String cityName;
    private WeatherData weatherDataObject;
    private WeatherDatabase weatherDatabase;
    private CityDatabase cityDatabase;
    private static String urlImage = "http://openweathermap.org/img/w/";
    private DatabaseHelper db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= new DatabaseHelper(getActivity());
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather_today, null);

        tvNameCity = (TextView) view.findViewById(R.id.tv_name_city);
        Log.d("VA tag", "tvNameCity = " + tvNameCity);
        tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
        tvTemp = (TextView) view.findViewById(R.id.tv_temp);
        tvInfoWeather = (TextView) view.findViewById(R.id.tv_infor_weather);
        imgIcoWeather = (ImageView) view.findViewById(R.id.img_ico_weather);

        tvTempNow = (TextView) view.findViewById(R.id.tv_temp_now);
        tvHumidityNow = (TextView) view.findViewById(R.id.tv_humidity_now);
        tvPressureNow = (TextView) view.findViewById(R.id.tv_pressure_now);


        if (isNetworkOnline()) {
            if (weatherDataObject != null) {
                Log.d("VA tag", "weatherDataObject = " + weatherDataObject.getCity().getName());
                tvNameCity.setText(weatherDataObject.getCity().getName());
                getDateTime();

                tvDateTime.setText(strHour + ":" + strMinute + " / " + strDayOfWeek + ", " + strDayOfMonth + ", " + strMonthOfYear);
                tvTemp.setText("" + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTemp())) + "\u2103");
                tvInfoWeather.setText("" + weatherDataObject.getList().get(0).getWeather().get(0).getMain());

                tvTempNow.setText("" + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin())) + "\u2103" + " / " + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin())) + "\u2103");
                tvHumidityNow.setText("" + weatherDataObject.getList().get(0).getMain().getHumidity() + "%");
                tvPressureNow.setText("" + weatherDataObject.getList().get(0).getMain().getPressure() + "hPa");

                ImageLoader imageLoader = new ImageLoader(getActivity());
                imageLoader.execute("" + urlImage + weatherDataObject.getList().get(0).getWeather().get(0).getIcon() + ".png");
            }
        } else {
            if (cityDatabase != null && weatherDatabase != null) {
                tvNameCity.setText(cityDatabase.getName());
                getDateTime();
                tvDateTime.setText(strHour + ":" + strMinute + " / " + strDayOfWeek + ", " + strDayOfMonth + ", " + strMonthOfYear);
                tvTemp.setText(weatherDatabase.getTemp());
                tvInfoWeather.setText(weatherDatabase.getDescription());

                tvTempNow.setText(weatherDatabase.getTempMin() + " / " + weatherDatabase.getTempMax());
                tvHumidityNow.setText(weatherDatabase.getHumidity());
                tvPressureNow.setText(weatherDatabase.getPressure() + "hPa");

                Tool.useIconOffline(getActivity(), weatherDatabase.getIcon(), imgIcoWeather);

            }
        }
        return view;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
//        if (tvNameCity != null) {
//            tvNameCity.setText(weatherDataObject.getCity().getName());
//        }
    }

    public void setData(WeatherData data) {
        this.weatherDataObject = data;
        if (tvPressureNow != null) {
            tvNameCity.setText(weatherDataObject.getCity().getName());
            getDateTime();
            tvDateTime.setText(strHour + ":" + strMinute + " / " + strDayOfWeek + ", " + strDayOfMonth + ", " + strMonthOfYear);
            tvTemp.setText("" + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTemp())) + "\u2103");
            tvInfoWeather.setText("" + weatherDataObject.getList().get(0).getWeather().get(0).getMain());

            tvTempNow.setText("" + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin())) + "\u2103" + " / " + Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin())) + "\u2103");
            tvHumidityNow.setText("" + weatherDataObject.getList().get(0).getMain().getHumidity() + "%");
            tvPressureNow.setText("" + weatherDataObject.getList().get(0).getMain().getPressure() + "hPa");

            ImageLoader imageLoader = new ImageLoader(getActivity());
            imageLoader.execute("" + urlImage + weatherDataObject.getList().get(0).getWeather().get(0).getIcon() + ".png");
        }
    }

    public void setDataOffline(CityDatabase cityDatabase, WeatherDatabase weatherDatabase) {
        this.cityDatabase = cityDatabase;
        this.weatherDatabase = weatherDatabase;
        if (tvPressureNow != null) {
            Log.d("cityDatabase", "" + cityDatabase.getName());
            tvNameCity.setText(cityDatabase.getName());
            getDateTime();
            tvDateTime.setText(strHour + ":" + strMinute + " / " + strDayOfWeek + ", " + strDayOfMonth + ", " + strMonthOfYear);
            tvTemp.setText(weatherDatabase.getTemp());
            tvInfoWeather.setText(weatherDatabase.getDescription());

            tvTempNow.setText(weatherDatabase.getTempMin() + " / " + weatherDatabase.getTempMax());
            tvHumidityNow.setText(weatherDatabase.getHumidity());
            tvPressureNow.setText(weatherDatabase.getPressure() + "hPa");

            Tool.useIconOffline(getActivity(), weatherDatabase.getIcon(), imgIcoWeather);

        }
    }

    public void getDateTime() {

        Date date = new Date();
        strHour = (String) android.text.format.DateFormat.format("HH", date);
        strMinute = (String) android.text.format.DateFormat.format("mm", date);
        strDayOfWeek = (String) android.text.format.DateFormat.format("EEEE", date);
        strMonthOfYear = (String) android.text.format.DateFormat.format("MMM", date);
        strDayOfMonth = (String) android.text.format.DateFormat.format("dd", date);
    }

    public boolean hadData() {
        if (weatherDataObject != null) {
            return true;
        } else return false;
    }

    class ImageLoader extends ImageDownloader {

        public ImageLoader(Context mContext) {
            super(mContext);
        }

        @Override
        protected Bitmap doInBackground(String... param) {

            try {
                Bitmap bitmap = null;
                URL imageUrl = new URL(param[0]);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setInstanceFollowRedirects(true);
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imgIcoWeather.setImageBitmap(result);
        }
    }

}
