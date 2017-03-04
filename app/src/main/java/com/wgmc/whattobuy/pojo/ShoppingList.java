package com.wgmc.whattobuy.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by notxie on 27.01.17.
 */

public class ShoppingList {
    private long id;
    private String name;
    private Shop whereToBuy;
    private List<Item> items;
    private Date dueTo;
    private List<Store> store;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Store> getStore() {
        return store;
    }

    public void setStore(List<Store> store) {
        this.store = store;
    }

    public ShoppingList(long id) {
        this.id = id;
        this.name = "";
        this.whereToBuy = null;
        this.items = new ArrayList<>();
        this.dueTo = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shop getWhereToBuy() {
        return whereToBuy;
    }

    public void setWhereToBuy(Shop whereToBuy) {
        this.whereToBuy = whereToBuy;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public boolean removeItem(Object o) {
        return items.remove(o);
    }

    public void clearItems() {
        items.clear();
    }

    public Date getDueTo() {
        return dueTo;
    }

    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }
}
