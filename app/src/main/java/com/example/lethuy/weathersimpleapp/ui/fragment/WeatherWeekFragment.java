package com.example.lethuy.weathersimpleapp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.data.WeatherData;
import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;
import com.example.lethuy.weathersimpleapp.database.WeatherDatabase;
import com.example.lethuy.weathersimpleapp.network.ImageDownloader;
import com.example.lethuy.weathersimpleapp.ui.activity.WeatherActivity;
import com.example.lethuy.weathersimpleapp.util.Tool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nguyen Manh Quan on 4/4/2016.
 */

public class WeatherWeekFragment extends Fragment {

    private TextView tvDayOfWeekOne, tvTempDayOfWeekOne, tvDayOfWeekTwo, tvTempDayOfWeekTwo;
    private ImageView imgWeatherDayOfWeekOne, imgWeatherDayOfWeekTwo;
    private WeatherData weatherDataObject;
    private int position;
    private SimpleDateFormat formatToDate, formatToString;
    private String cityName;
    private WeatherDatabase weatherDatabaseOne, weatherDatabaseTwo;
    private DatabaseHelper db;
    private WeatherActivity weatherActivity;
    private ProgressDialog pDialog;

    public WeatherWeekFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
        weatherActivity = new WeatherActivity();
    }

    @SuppressLint("ValidFragment")
    public WeatherWeekFragment(int position) {
        this.position = position;
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

    private static String urlImage = "http://openweathermap.org/img/w/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_in_week, null);

        tvDayOfWeekOne = (TextView) view.findViewById(R.id.tv_day_of_week_one);
        tvTempDayOfWeekOne = (TextView) view.findViewById(R.id.tv_temp_day_of_week_one);
        tvDayOfWeekTwo = (TextView) view.findViewById(R.id.tv_day_of_week_two);
        tvTempDayOfWeekTwo = (TextView) view.findViewById(R.id.tv_temp_day_of_week_two);
        imgWeatherDayOfWeekOne = (ImageView) view.findViewById(R.id.img_weather_day_of_week_one);
        imgWeatherDayOfWeekTwo = (ImageView) view.findViewById(R.id.img_weather_day_of_week_two);

        formatToDate = new SimpleDateFormat("yyyy-MM-dd");
        formatToString = new SimpleDateFormat("EEEE");

        if(isNetworkOnline()){
            if (weatherDataObject != null) {

                String dateStringOne = weatherDataObject.getList().get(position).getDtTxt().substring(0, 10);

                Date dateOne = null;
                try {
                    dateOne = formatToDate.parse(dateStringOne);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String dateStringTwo = weatherDataObject.getList().get(position + 8).getDtTxt().substring(0, 10);

                Date dateTwo = null;
                try {
                    dateTwo = formatToDate.parse(dateStringTwo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tvDayOfWeekOne.setText("" + formatToString.format(dateOne));
                tvTempDayOfWeekOne.setText("" + Tool.convertKenvilToCelcius(Float.valueOf("" + weatherDataObject.getList().get(position).getMain().getTemp())) + "\u2103");
                tvDayOfWeekTwo.setText("" + formatToString.format(dateTwo));
                tvTempDayOfWeekTwo.setText("" + Tool.convertKenvilToCelcius(Float.valueOf("" + weatherDataObject.getList().get(position + 8).getMain().getTemp())) + "\u2103");
                ImageLoaderWeatherDayOne imageLoaderOne = new ImageLoaderWeatherDayOne(getActivity());
                imageLoaderOne.execute("" + urlImage + weatherDataObject.getList().get(position).getWeather().get(0).getIcon() + ".png");

                ImageLoaderWeatherDayTwo imageLoaderTwo = new ImageLoaderWeatherDayTwo(getActivity());
                imageLoaderTwo.execute("" + urlImage + weatherDataObject.getList().get(position + 8).getWeather().get(0).getIcon() + ".png");

            }
        } else {
            if(weatherDatabaseOne != null && weatherDatabaseTwo != null){
                String dateStringOne = weatherDatabaseOne.getDate();
                Date dateOne = null;
                try {
                    dateOne = formatToDate.parse(dateStringOne);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String dateStringTwo = weatherDatabaseTwo.getDate();

                Date dateTwo = null;
                try {
                    dateTwo = formatToDate.parse(dateStringTwo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tvDayOfWeekOne.setText("" + formatToString.format(dateOne));
                tvTempDayOfWeekOne.setText("" + weatherDatabaseOne.getTemp());
                tvDayOfWeekTwo.setText("" + formatToString.format(dateTwo));
                tvTempDayOfWeekTwo.setText("" + weatherDatabaseTwo.getTemp());

                Tool.useIconOffline(getActivity(), weatherDatabaseOne.getIcon(), imgWeatherDayOfWeekOne);
                Tool.useIconOffline(getActivity(), weatherDatabaseTwo.getIcon(), imgWeatherDayOfWeekTwo);

            }
        }
        return view;
    }

    public void setWeatherData(WeatherData weatherDataObject, int position) {
        this.weatherDataObject = weatherDataObject;
        if (imgWeatherDayOfWeekTwo != null) {

            String dateStringOne = weatherDataObject.getList().get(position).getDtTxt().substring(0, 10);
            Date dateOne = null;
            try {
                dateOne = formatToDate.parse(dateStringOne);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            String dateStringTwo = weatherDataObject.getList().get(position + 8).getDtTxt().substring(0, 10);

            Date dateTwo = null;
            try {
                dateTwo = formatToDate.parse(dateStringTwo);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvDayOfWeekOne.setText("" + formatToString.format(dateOne));
            tvTempDayOfWeekOne.setText("" + Tool.convertKenvilToCelcius(Float.valueOf("" + weatherDataObject.getList().get(position).getMain().getTemp())) + "\u2103");
            tvDayOfWeekTwo.setText("" + formatToString.format(dateTwo));
            tvTempDayOfWeekTwo.setText("" + Tool.convertKenvilToCelcius(Float.valueOf("" + weatherDataObject.getList().get(position + 8).getMain().getTemp())) + "\u2103");

            ImageLoaderWeatherDayOne imageLoaderOne = new ImageLoaderWeatherDayOne(getActivity());
            imageLoaderOne.execute("" + urlImage + weatherDataObject.getList().get(position).getWeather().get(0).getIcon() + ".png");

            ImageLoaderWeatherDayTwo imageLoaderTwo = new ImageLoaderWeatherDayTwo(getActivity());
            imageLoaderTwo.execute("" + urlImage + weatherDataObject.getList().get(position + 8).getWeather().get(0).getIcon() + ".png");

        }

    }


    public void setDataOffline(WeatherDatabase weatherDatabaseOne, WeatherDatabase weatherDatabaseTwo) {
        this.weatherDatabaseOne = weatherDatabaseOne;
        this.weatherDatabaseTwo = weatherDatabaseTwo;
        if (imgWeatherDayOfWeekTwo != null) {

            String dateStringOne = weatherDatabaseOne.getDate();
            Date dateOne = null;
            try {
                dateOne = formatToDate.parse(dateStringOne);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            String dateStringTwo = weatherDatabaseTwo.getDate();

            Date dateTwo = null;
            try {
                dateTwo = formatToDate.parse(dateStringTwo);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvDayOfWeekOne.setText("" + formatToString.format(dateOne));
            tvTempDayOfWeekOne.setText("" + weatherDatabaseOne.getTemp());
            tvDayOfWeekTwo.setText("" + formatToString.format(dateTwo));
            tvTempDayOfWeekTwo.setText("" + weatherDatabaseTwo.getTemp());

            Tool.useIconOffline(getActivity(), weatherDatabaseOne.getIcon(), imgWeatherDayOfWeekOne);
            Tool.useIconOffline(getActivity(), weatherDatabaseTwo.getIcon(), imgWeatherDayOfWeekTwo);


        }

    }

    public boolean hadData() {
        if (weatherDataObject != null) {
            return true;
        } else return false;
    }

    class ImageLoaderWeatherDayOne extends ImageDownloader {

        public ImageLoaderWeatherDayOne(Context mContext) {
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
            imgWeatherDayOfWeekOne.setImageBitmap(result);
            //db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(8).getMain().getHumidity() + "%"), weatherDataObject.getList().get(8).getMain().getPressure(), weatherDataObject.getList().get(8).getWeather().get(0).getMain(), weatherActivity.saveToInternalStorage(result), weatherDataObject.getList().get(8).getDt_txt().substring(11, 19), weatherDataObject.getList().get(8).getDt_txt().substring(0, 10)));
        }
    }

    class ImageLoaderWeatherDayTwo extends ImageDownloader {

        public ImageLoaderWeatherDayTwo(Context mContext) {
            super(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (getActivity().isFinishing() == false) {
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
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
            imgWeatherDayOfWeekTwo.setImageBitmap(result);
//            db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(0).getMain().getHumidity() + "%"), weatherDataObject.getList().get(0).getMain().getPressure(), weatherDataObject.getList().get(0).getWeather().get(0).getMain(), weatherDataObject.getList().get(0).getWeather().get(0).getIcon(), weatherDataObject.getList().get(0).getDt_txt().substring(11, 19), weatherDataObject.getList().get(0).getDt_txt().substring(0, 10)));
//            db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(8).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(8).getMain().getHumidity() + "%"), weatherDataObject.getList().get(8).getMain().getPressure(), weatherDataObject.getList().get(8).getWeather().get(0).getMain(), weatherDataObject.getList().get(8).getWeather().get(0).getIcon(), weatherDataObject.getList().get(8).getDt_txt().substring(11, 19), weatherDataObject.getList().get(8).getDt_txt().substring(0, 10)));
//            db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(16).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(16).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(16).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(16).getMain().getHumidity() + "%"), weatherDataObject.getList().get(16).getMain().getPressure(), weatherDataObject.getList().get(16).getWeather().get(0).getMain(), weatherDataObject.getList().get(16).getWeather().get(0).getIcon(), weatherDataObject.getList().get(16).getDt_txt().substring(11, 19), weatherDataObject.getList().get(16).getDt_txt().substring(0, 10)));
//            db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(24).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(24).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(24).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(24).getMain().getHumidity() + "%"), weatherDataObject.getList().get(24).getMain().getPressure(), weatherDataObject.getList().get(24).getWeather().get(0).getMain(), weatherDataObject.getList().get(24).getWeather().get(0).getIcon(), weatherDataObject.getList().get(24).getDt_txt().substring(11, 19), weatherDataObject.getList().get(24).getDt_txt().substring(0, 10)));
//            db.addWeather(new WeatherDatabase(weatherDataObject.getCity().getId(), String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(0).getMain().getTemp()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(32).getMain().getTempMin()))) + "\u2103", String.valueOf(Tool.convertKenvilToCelcius(Float.parseFloat("" + weatherDataObject.getList().get(32).getMain().getTempMax()))) + "\u2103", String.valueOf(weatherDataObject.getList().get(32).getMain().getHumidity() + "%"), weatherDataObject.getList().get(32).getMain().getPressure(), weatherDataObject.getList().get(32).getWeather().get(0).getMain(), weatherDataObject.getList().get(32).getWeather().get(0).getIcon(), weatherDataObject.getList().get(32).getDt_txt().substring(11, 19), weatherDataObject.getList().get(32).getDt_txt().substring(0, 10)));

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
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

}