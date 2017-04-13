package com.wgmc.whattobuy.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wgmc.whattobuy.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by proxie on 5.4.17.
 */
// settings managment handling saving and loading of system settings and preferences
public class SettingsService extends DefaultService {
    private static SettingsService instance;

    public static SettingsService getInstance() {
        if (instance == null)
            instance = new SettingsService();
        return instance;
    }

    private static final String prefName = "com.wgmc.whattobuy.settings";

    // Settings Keys:
    public static String SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS;
    public static String SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL;

    private Map<String, Object> settings;

    private SettingsService() {
        settings = new HashMap<>();
    }

    public void loadSettings(Activity c) {
        SharedPreferences sp = c.getPreferences(Context.MODE_PRIVATE);

        Map<String, ?> all = sp.getAll();

        for (Map.Entry<String, ?> e : all.entrySet()) {
            settings.put(e.getKey(), e.getValue());
        }
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public Object getSetting(String key) {
        return settings.get(key);
    }

    public void putSetting(String key, Object val) {
        settings.put(key, val);
    }

    public void saveSettings(Activity c) {
        SharedPreferences pref = c.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        for (Map.Entry<String, Object> e : settings.entrySet()) {
            Log.d("Settings", e.getKey() + ": " + e.getValue());
            editor.putString(e.getKey(), e.getValue().toString());
        }

//        editor.commit();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                editor.commit();
            }
        }, "settings-saver");
        t.setDaemon(true);
        t.start();
    }

    public void initSettingKeys(Activity a) {
        SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS = a.getString(R.string.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS);
        SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL = a.getString(R.string.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL);
    }
}
