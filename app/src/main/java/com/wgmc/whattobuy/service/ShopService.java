package com.wgmc.whattobuy.service;

import android.util.SparseArray;

import com.wgmc.whattobuy.pojo.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proxie on 27.03.17.
 */

public class ShopService extends DefaultService {
    private static ShopService instance;

    public static ShopService getInstance() {
        return instance;
    }

    public static void generateInstance() {
        instance = new ShopService();
    }

    public static void destroyInstance() {
        instance = null;
    }

    private List<Shop> shops;
    private SparseArray<Shop> assignedShops;

    private ShopService() {
        shops = new ArrayList<>();
        assignedShops = new SparseArray<>();

        System.out.println("init shops");
        for (Shop s : MainService.getInstance().getDb().getAllShops()) {
            System.out.println("in loop");
            rawAdd(s);
        }
    }

    public List<Shop> getShops() {
        return shops;
    }

    public Shop getShopById(int id) {
        return assignedShops.get(id);
    }

    private void rawAdd(Shop s) {
        shops.add(s);
        assignedShops.put((int) s.getId(), s);
    }

    private void rawRemove(Shop s) {
        shops.remove(s);
        assignedShops.remove((int) s.getId());
    }

    public void addShop(Shop s) {
        if (s.getId() < 0) {
            rawAdd(s);
            MainService.getInstance().getDb().createShop(s);
        } else {
            MainService.getInstance().getDb().updateShop(s);
        }
        notifyObservers(s);
    }

    public void removeShop(Shop s) {
        if (s.getId() > 0) {
            rawRemove(s);
            MainService.getInstance().getDb().deleteShop(s);
            notifyObservers();
        }
    }
}
