package com.example.framgia.weathersimple.service.remote;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.framgia.weathersimple.data.CityObject;
import com.example.framgia.weathersimple.data.CloudsObject;
import com.example.framgia.weathersimple.data.CoordObject;
import com.example.framgia.weathersimple.data.ListObject;
import com.example.framgia.weathersimple.data.MainObject;
import com.example.framgia.weathersimple.data.WeatherDataObject;
import com.example.framgia.weathersimple.data.WeatherObject;
import com.example.framgia.weathersimple.data.WindObject;
import com.example.framgia.weathersimple.network.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by framgia on 30/03/2016.
 */
public class GetWeatherDataObject extends AsyncTask<String, Void, JSONObject> {

    private Context mContext;
    private WeatherDataObject weatherObjectData;

    private ProgressDialog pDialog;




    public GetWeatherDataObject(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected JSONObject doInBackground(String... arg0) {
        // Creating service handler class instance


        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();


//            Toast.makeText(MainActivity.this, ""+weatherObjectData.getCity().getName(), Toast.LENGTH_SHORT).show();
//            Log.d("Quan quan", "" + weatherObjectData.toString());


    }
}