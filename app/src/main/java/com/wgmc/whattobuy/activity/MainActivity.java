package com.wgmc.whattobuy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wgmc.whattobuy.service.MainService;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "whattobuy_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainService.generateInstance();
        MainService.getInstance().setPreferences(getSharedPreferences(PREF_NAME, MODE_PRIVATE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainService.destroyInstance();
    }
}