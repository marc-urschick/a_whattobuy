package com.wgmc.whattobuy.service;

import android.util.SparseArray;

import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class ShoplistService extends DefaultService {
    public static ShoplistService getInstance() {
        if (instance == null)
            instance = new ShoplistService();
        return instance;
    }

    private static ShoplistService instance;

    private List<ShoppingList> shoppingLists;
    private SparseArray<ShoppingList> assignedShoppingLists;

    public static final DateFormat displayDateFormat = new SimpleDateFormat("dd. MM. yyyy", Locale.getDefault());

    private ShoplistService() {
        shoppingLists = new ArrayList<>();
        assignedShoppingLists = new SparseArray<>();

        for (ShoppingList l : MainService.getInstance().getDb().getAllShoppingLists()) {
            rawAdd(l);
        }
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public ShoppingList getShoppingListById(int id) {
        return assignedShoppingLists.get(id);
    }

    private void rawAdd(ShoppingList l) {
        shoppingLists.add(l);
        assignedShoppingLists.put((int) l.getId(), l);
    }

    private void rawRemove(ShoppingList list) {
        shoppingLists.remove(list);
        assignedShoppingLists.remove((int) list.getId());
    }

    public void addShoppingList(ShoppingList l) {
        if (l == null)
            return;

        if (l.getId() < 0) {
            MainService.getInstance().getDb().createList(l);
            rawAdd(l);
        } else {
            MainService.getInstance().getDb().updateList(l);
        }

        for (Item i : l.getItems()) {
            ItemService.getInstance().addItem(i);
        }
        notifyObservers(l);
    }

    public void removeShoppingList(ShoppingList list) {
        if (list == null)
            return;

        if (list.getId() > 0) {
            MainService.getInstance().getDb().deleteShoppingList(list);
            rawRemove(list);
            notifyObservers();
        }

        for (Item i : list.getItems()) {
            ItemService.getInstance().removeItem(i);
        }
    }
}
