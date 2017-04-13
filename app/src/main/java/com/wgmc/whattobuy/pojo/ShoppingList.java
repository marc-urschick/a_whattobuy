package com.wgmc.whattobuy.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by notxie, poidl on 27.01.17. and 10.04.2017.
 */

public class ShoppingList {
    private long id;
    //Declare the variable id with the data typ long
    private String name;
    //Declare the variable name with the data typ String
    private Shop whereToBuy;
    //Declare the variable whereToBuy with the data typ Shop as a other Class
    private List<Item> items;
    //Declare the variable items with the data typ generic List as a other Class
    private Date dueTo;
    //Declare the variable dueTo with the data typ Date

    public ShoppingList(long id) {
        this.id = id;
        this.name = "";
        this.whereToBuy = null;
        this.items = new ArrayList<>();
        this.dueTo = new Date();
        //This is the constructor for the Class ShoppingList and we initate all the declarations
    }

    //Getter for id
    public long getId() {
        return id;
    }

    //Setter for id
    public void setId(long id) {
        this.id = id;
    }

    //Getter for name
    public String getName() {
        return name;
    }

    //Setter for name
    public void setName(String name) {
        this.name = name;
    }

    //Getter for WhereToBuy
    public Shop getWhereToBuy() {
        return whereToBuy;
    }

    //Setter for WhereToBuy
    public void setWhereToBuy(Shop whereToBuy) {
        this.whereToBuy = whereToBuy;
    }

    //Method for return the items of the List
    public List<Item> getItems() {
        return items;
    }

    //Method fpr add items
    public boolean addItem(Item item) {
        return items.add(item);
    }

    //Method for set the boolean value ot the obect o
    public boolean removeItem(Object o) {
        return items.remove(o);
    }

    //Method for clear the items
    public void clearItems() {
        items.clear();
    }

    //Getter for dueTo
    public Date getDueTo() {
        return dueTo;
    }

    //Setter for dueTo
    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }
}
