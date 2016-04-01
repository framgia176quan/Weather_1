package com.example.lethuy.weathersimpleapp.network;

/**
 * Created by framgia on 29/03/2016.
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by framgia on 29/03/2016.
 */


public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }


    public String makeServiceCall(String textUrl, int method) {
        InputStream in = null;
        BufferedReader br= null;
        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
                br= new BufferedReader(new InputStreamReader(in));

                StringBuilder sb= new StringBuilder();
                String s= null;
                while((s= br.readLine())!= null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}