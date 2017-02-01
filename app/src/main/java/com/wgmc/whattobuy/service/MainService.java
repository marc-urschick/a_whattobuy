package com.wgmc.whattobuy.service;

import android.content.SharedPreferences;

/**
 * Created by notxie on 27.01.17.
 */

public class MainService {
    private static MainService instance;

    public static MainService getInstance() {
        return instance;
    }

    public static void generateInstance() throws IllegalStateException {
        if (instance != null) {
            throw new IllegalStateException("There can only be one instance of MainService");
        }
        instance = new MainService();
    }

    public static void destroyInstance() throws IllegalStateException {
        if (instance == null) {
            throw new IllegalStateException("No instance present to be destroyed");
        }
        instance = null;
    }

    private SharedPreferences preferences;

    private MainService() {

    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
