package com.example.framgia.weathersimple.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.weathersimple.R;
import com.example.framgia.weathersimple.data.WeatherDataObject;
import com.example.framgia.weathersimple.service.remote.GetWeatherDataObject;





public class CityFragment extends Fragment {

    TextView tvNameCity, tvDateTime, tvTemp, tvInfoWeather, tvTempNow, tvHumidityNow, tvWindNow;
    ImageView imgIcoWeather, imgIcoTempNow,imgIcoHumidityNow, imgIcoWindNow;
    ConnectivityManager connMgr;

    NetworkInfo networkInfo;
    WeatherDataObject weatherDataObject;
    String nameCity;

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
        protected void onPostExecute(WeatherDataObject result) {
            super.onPostExecute(result);
            weatherDataObject= result;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connMgr = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        weatherDataObject= new WeatherDataObject();
        if(isNetwork()){
            GetData getData = new GetData(getActivity());
            getData.execute(nameCity);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);


        tvNameCity = (TextView) container.findViewById(R.id.tv_name_city);
        tvNameCity.setText(""+weatherDataObject.getCity().getName());
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
