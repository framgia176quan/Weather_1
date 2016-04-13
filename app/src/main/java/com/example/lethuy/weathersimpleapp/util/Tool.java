package com.example.lethuy.weathersimpleapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.example.lethuy.weathersimpleapp.R;

/**
 * Created by Nguyen Manh Quan on 31/03/2016.
 */
public class Tool {
    public static float convertKenvilToCelcius(float kenvil) {
        float fahrenheit = (float) (1.8*(kenvil-273)+32);
        return Math.round(((fahrenheit - 32) * 5 / 9));
    }


    public static void useIconOffline(Context context, String strIcon, ImageView imageView) {
        switch (strIcon) {
            case "01d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_clear_sky));
                break;
            case "02d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_few_clouds));
                break;
            case "03d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_scattered_clouds));
                break;
            case "04d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_broken_clouds));
                break;
            case "09d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_shower_rain));
                break;
            case "10d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_rain));
                break;
            case "11d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_thunderstorm));
                break;
            case "13d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_snow));
                break;
            case "50d":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.d_mist));
                break;
            case "01n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_clear_sky));
                break;
            case "02n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_few_clouds));
                break;
            case "03n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_scattered_clouds));
                break;
            case "04n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_broken_clouds));
                break;
            case "09n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_shower_rain));
                break;
            case "10n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_rain));
                break;
            case "11n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_thunderstorm));
                break;
            case "13n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_snow));
                break;
            case "50n":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.n_mist));
                break;

        }
    }

}
