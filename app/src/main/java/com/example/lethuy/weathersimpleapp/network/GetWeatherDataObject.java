package com.example.lethuy.weathersimpleapp.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.lethuy.weathersimpleapp.data.WeatherData;

import org.json.JSONObject;

/**
 * Created by Nguyen Manh Quan on 30/03/2016.
 */
public class GetWeatherDataObject extends AsyncTask<String, Void, JSONObject> {

    private Context mContext;
    private WeatherData weatherObjectData;
    private ProgressDialog pDialog;


    public GetWeatherDataObject(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
//        if (!isFinishing()) {
//            pDialog = new ProgressDialog(mContext);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }

//        pDialog = ProgressDialog.show(mContext, "Please wait...","Retrieving GPS data ...", true);

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
//        if (loginActivityWeakRef.get() != null && !loginActivityWeakRef.get().isFinishing())
//        if (pDialog != null)
//            pDialog.dismiss();
    }
}