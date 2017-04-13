package com.wgmc.whattobuy.service;

import com.wgmc.whattobuy.persistence.BuylistOpenHelper;

/**
 * Created by notxie on 27.01.17.
 */
// service for managing database holding
public class MainService extends DefaultService {
    private static MainService instance;

    public static MainService getInstance() {
        if (instance == null)
            instance = new MainService();
        return instance;
    }

    private BuylistOpenHelper db;

    private MainService() {

    }

    public BuylistOpenHelper getDb() {
        return db;
    }

    public void setDb(BuylistOpenHelper db) throws Exception {
        this.db = db;
    }
}
