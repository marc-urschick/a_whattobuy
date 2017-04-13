package com.wgmc.whattobuy.service;

import android.util.SparseArray;

import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.pojo.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proxie on 27.03.17.
 */

public class ShopService extends DefaultService {
    private static ShopService instance;

    public static ShopService getInstance() {
        if (instance == null)
            instance = new ShopService();
        return instance;
    }

    private List<Shop> shops;
    private SparseArray<Shop> assignedShops;

    private ShopService() {
        shops = new ArrayList<>();
        assignedShops = new SparseArray<>();

        for (Shop s : MainService.getInstance().getDb().getAllShops()) {
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
            MainService.getInstance().getDb().createShop(s);
            rawAdd(s);
        } else {
            MainService.getInstance().getDb().updateShop(s);
        }
        notifyObservers(s);
    }

    public void removeShop(Shop s) {
        long sid = s.getId();

        if (sid > 0) {
            MainService.getInstance().getDb().deleteShop(s);
            rawRemove(s);
            notifyObservers();

            for (ShoppingList list : ShoplistService.getInstance().getShoppingLists()) {
                if (list.getWhereToBuy().getId() == sid) {
                    ShoplistService.getInstance().removeShoppingList(list);
                }
            }
        }
    }
}
