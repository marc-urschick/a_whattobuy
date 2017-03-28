package com.wgmc.whattobuy.service;

import android.content.SharedPreferences;

import com.wgmc.whattobuy.persistence.BuylistOpenHelper;

/**
 * Created by notxie on 27.01.17.
 */

public class MainService extends DefaultService {
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

    private BuylistOpenHelper db;

    private MainService() {

    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public BuylistOpenHelper getDb() {
        return db;
    }

    public void setDb(BuylistOpenHelper db) throws Exception {
        this.db = db;
    }
}
