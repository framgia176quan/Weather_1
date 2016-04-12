package com.example.lethuy.weathersimpleapp.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Nguyen Manh Quan on 01/04/2016.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
//    private ProgressDialog pDialog;

    public ImageDownloader(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    protected void onPreExecute() {
//        pDialog = new ProgressDialog(mContext);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();
    }


    @Override
    protected Bitmap doInBackground(String... param) {
        // TODO Auto-generated method stub
       return null;
    }


    @Override
    protected void onPostExecute(Bitmap result) {
//        if (pDialog.isShowing())
//            pDialog.dismiss();

    }
}
