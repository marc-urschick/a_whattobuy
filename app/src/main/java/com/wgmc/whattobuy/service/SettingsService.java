package com.wgmc.whattobuy.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by proxie on 5.4.17.
 */

public class SettingsService extends DefaultService {
    private static SettingsService instance;

    public static SettingsService getInstance() {
        return instance;
    }

    public static void createInstance() {
        instance = new SettingsService();
    }

    public static void destroyInstance() {
        instance = null;
    }

    private static final String prefName = "com.wgmc.whattobuy.settings";

    // Settings Keys:
    public static final String SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS = "enShChIt";

    private Map<String, Object> settings;

    private SettingsService() {
        settings = new HashMap<>();
    }

    public void loadSettings(Context c) {
        SharedPreferences sp = c.getSharedPreferences(prefName, Context.MODE_PRIVATE);

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

    public void saveSettings(Context c) {
        SharedPreferences pref = c.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        for (Map.Entry<String, Object> e : settings.entrySet()) {
            editor.putString(e.getKey(), e.getValue().toString());
        }

        editor.apply();
    }
}
