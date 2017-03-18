package com.wgmc.whattobuy.service;

import android.util.SparseArray;

import com.wgmc.whattobuy.pojo.ShoppingList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class ShoplistService extends Observable {
    public static void generateInstance() {
        instance = new ShoplistService();
    }

    public static void destroyInstance() {
        instance = null;
    }

    public static ShoplistService getInstance() {
        return instance;
    }

    private static ShoplistService instance;

    private List<ShoppingList> shoppingLists;
    private SparseArray<ShoppingList> assignedShoppingLists;

    public static final DateFormat displayDateFormat = new SimpleDateFormat("dd. MM. yyyy");

    private List<Observer> observers = new ArrayList<>();

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(Object arg) {
        for (Observer o : observers) {
            o.update(this, arg);
        }
    }

    private ShoplistService() {
        shoppingLists = new ArrayList<>();
        assignedShoppingLists = new SparseArray<>();
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public ShoppingList getShoppingListById(int id) {
        return assignedShoppingLists.get(id);
    }

    public void addShoppingList(ShoppingList list) {
        if (list.getId() > 0) {
            shoppingLists.add(list);
            assignedShoppingLists.put((int) list.getId(), list);
        }
    }
}
