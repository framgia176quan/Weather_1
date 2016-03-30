package com.example.framgia.weathersimple.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.weathersimple.R;
import com.example.framgia.weathersimple.data.WeatherDataObject;
import com.example.framgia.weathersimple.ui.activity.MainActivity;

import java.util.ArrayList;




public class CityFragment extends Fragment {

    TextView tvNameCity, tvDateTime, tvTemp, tvInfoWeather, tvTempNow, tvHumidityNow, tvWindNow;
    ImageView imgIcoWeather, imgIcoTempNow,imgIcoHumidityNow, imgIcoWindNow;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        WeatherDataObject weatherDataObject = (WeatherDataObject) getArguments().getSerializable(MainActivity.KEY_SEND_DATA_CITY);
//        String a = getArguments().getString(MainActivity.KEY_SEND_DATA_CITY);

        tvNameCity = (TextView) container.findViewById(R.id.tv_name_city);
        tvDateTime = (TextView) container.findViewById(R.id.tv_date_time);
        tvTemp = (TextView) container.findViewById(R.id.tv_temp);
        tvInfoWeather = (TextView) container.findViewById(R.id.tv_infor_weather);
        tvTempNow = (TextView) container.findViewById(R.id.tv_temp_now);
        tvHumidityNow = (TextView) container.findViewById(R.id.tv_humidity_now);
        tvWindNow = (TextView) container.findViewById(R.id.tv_wind_now);

        imgIcoWeather = (ImageView) container.findViewById(R.id.img_ico_weather);
        imgIcoTempNow = (ImageView) container.findViewById(R.id.img_ico_temp_now);
        imgIcoHumidityNow = (ImageView) container.findViewById(R.id.img_ico_humidity_now);
        imgIcoWindNow = (ImageView) container.findViewById(R.id.img_ico_wind_now);


        return view;
    }
}
