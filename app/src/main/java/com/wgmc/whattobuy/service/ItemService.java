package com.wgmc.whattobuy.service;

import android.util.SparseArray;

import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by proxie on 24.03.17.
 */

public class ItemService extends DefaultService {
    public static void generateInstance() {
        instance = new ItemService();
    }

    public static void destroyInstance() {
        instance = null;
    }

    public static ItemService getInstance() {
        return instance;
    }

    private static ItemService instance;

    private List<Item> items;
    private SparseArray<Item> assignedItems;

    public static final DateFormat displayDateFormat = new SimpleDateFormat("dd. MM. yyyy");

    private ItemService() {
        items = new ArrayList<>();
        assignedItems = new SparseArray<>();

        for (Item i : MainService.getInstance().getDb().getItemsInList(1)) {
            rawAdd(i);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemById(int id) {
        return assignedItems.get(id);
    }

    private void rawAdd(Item i) {
        items.add(i);
        assignedItems.put((int) i.getId(), i);
    }

    private void rawRemove(Item i) {
        items.remove(i);
        assignedItems.remove((int) i.getId());
    }

    public void addItem(Item i) {
        if (i.getId() > 0) {
            rawAdd(i);
            MainService.getInstance().getDb().createItem(i);
        } else {
            MainService.getInstance().getDb().updateItem(i);
        }
        notifyObservers(i);
    }

    public void removeItem(Item i) {
        if (i.getId() > 0) {
            rawRemove(i);
            MainService.getInstance().getDb().deleteItem(i);
            notifyObservers();
        }
    }
}
