package com.example.lethuy.weathersimpleapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.sharepreferences.SettingSharePreference;


/**
 * Created by Nguyen Manh Quan on 06/04/2016.
 */
public class SettingUpdateActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonOne, radioButtonTwo, radioButtonThree, radioButtonFour, radioButtonFive, radioButtonSix;

    public static String KEY_SETTING_UPDATE = "key_save";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_update);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_setting_update);
        radioButtonOne = (RadioButton) findViewById(R.id.radio_type_one);
        radioButtonTwo = (RadioButton) findViewById(R.id.radio_type_two);
        radioButtonThree = (RadioButton) findViewById(R.id.radio_type_three);
        radioButtonFour = (RadioButton) findViewById(R.id.radio_type_four);
        radioButtonFive = (RadioButton) findViewById(R.id.radio_type_five);
        radioButtonSix = (RadioButton) findViewById(R.id.radio_type_six);

        int numSetting = SettingSharePreference.getIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE);


        switch (numSetting) {

            case -1:
                ((RadioButton) findViewById(R.id.radio_type_one)).setChecked(true);
                break;
            case 0:
                ((RadioButton) findViewById(R.id.radio_type_one)).setChecked(true);
                break;
            case 5:
                ((RadioButton) findViewById(R.id.radio_type_two)).setChecked(true);
                break;
            case 10:
                ((RadioButton) findViewById(R.id.radio_type_three)).setChecked(true);
                break;
            case 15:
                ((RadioButton) findViewById(R.id.radio_type_four)).setChecked(true);
                break;
            case 30:
                ((RadioButton) findViewById(R.id.radio_type_five)).setChecked(true);
                break;
            case 60:
                ((RadioButton) findViewById(R.id.radio_type_six)).setChecked(true);
                break;
        }


        settingView();
    }

    public void settingView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_type_one:
                        SettingSharePreference.saveIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE, 0);
                        break;
                    case R.id.radio_type_two:
                        SettingSharePreference.saveIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE, 5);
                        break;
                    case R.id.radio_type_three:
                        SettingSharePreference.saveIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE, 10);
                        break;
                    case R.id.radio_type_four:
                        SettingSharePreference.saveIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE, 15);
                        break;
                    case R.id.radio_type_five:
                        SettingSharePreference.saveIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE, 30);
                        break;
                    case R.id.radio_type_six:
                        SettingSharePreference.saveIntPreference(SettingUpdateActivity.this, KEY_SETTING_UPDATE, 60);
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingUpdateActivity.this, WeatherActivity.class);
        startActivity(intent);
        finish();
    }
}
