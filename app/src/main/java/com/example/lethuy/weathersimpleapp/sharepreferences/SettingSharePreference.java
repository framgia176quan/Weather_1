package com.example.lethuy.weathersimpleapp.sharepreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nguyen Manh Quan on 16/10/2015.
 */

public class SettingSharePreference {

    public final static String PREF_KEY = "weather_simple";

    public static int getIntPreference(Context pContext, String strKey) {
        if (pContext.getSharedPreferences(PREF_KEY, 0) != null) {
            return pContext.getSharedPreferences(PREF_KEY, 0)
                    .getInt(strKey, -1);
        } else {
            return -1;
        }

    }

    public static boolean saveIntPreference(Context pContext, String strKey,
                                            int iValue) {
        if (pContext.getSharedPreferences(PREF_KEY, 0) != null) {

            SharedPreferences.Editor e = pContext.getSharedPreferences(PREF_KEY, 0)
                    .edit();
            e.putInt(strKey, iValue);
            e.commit();
            return true;
        }
        return false;
    }
}

