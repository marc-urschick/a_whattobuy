package com.wgmc.whattobuy.service;

import android.content.SharedPreferences;

import com.wgmc.whattobuy.persistence.BuylistOpenHelper;

/**
 * Created by notxie on 27.01.17.
 */

public class MainService extends DefaultService {
    private static MainService instance;

    public static MainService getInstance() {
        if (instance == null)
            instance = new MainService();
        return instance;
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
