package com.example.lethuy.weathersimpleapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.sharepreferences.SettingSharePreference;


/**
 * Created by Nguyen Manh Quan on 06/04/2016.
 */
public class SettingNotificationActivity extends Activity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonYes, radioButtonNo;

    public static String KEY_SETTING_NOTITICATION = "KEY_SETTING_NOTITICATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_notification);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_setting_notification);
        radioButtonYes = (RadioButton) findViewById(R.id.radio_type_yes);
        radioButtonNo = (RadioButton) findViewById(R.id.radio_type_no);

        int numSetting = SettingSharePreference.getIntPreference(SettingNotificationActivity.this, KEY_SETTING_NOTITICATION);
        switch (numSetting) {
            case -1:
                ((RadioButton) findViewById(R.id.radio_type_yes)).setChecked(true);
                ((RadioButton) findViewById(R.id.radio_type_no)).setChecked(false);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.radio_type_yes)).setChecked(true);
                ((RadioButton) findViewById(R.id.radio_type_no)).setChecked(false);
                break;
            case 0:
                ((RadioButton) findViewById(R.id.radio_type_yes)).setChecked(false);
                ((RadioButton) findViewById(R.id.radio_type_no)).setChecked(true);
                break;
        }


        settingView();
    }

    public void settingView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_type_yes:
                        SettingSharePreference.saveIntPreference(SettingNotificationActivity.this, KEY_SETTING_NOTITICATION, 1);
                        break;
                    case R.id.radio_type_no:
                        SettingSharePreference.saveIntPreference(SettingNotificationActivity.this, KEY_SETTING_NOTITICATION, 0);
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SettingNotificationActivity.this, WeatherActivity.class);
        startActivity(intent);
        finish();
    }
}
