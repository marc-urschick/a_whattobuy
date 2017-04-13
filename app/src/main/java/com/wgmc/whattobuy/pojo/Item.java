package com.wgmc.whattobuy.pojo;

import java.util.Locale;

/**
 * Created by notxie, poidl on 27.01.17. and 10.04.2017.
 */

public class Item {
    private long id;
    //Declare the variable id with the data typ long
    private String name;
    //Declare the variable name with the data typ String
    private String infos;
    //Declare the variable infos with the data typ String
    private String menge;
    //Declare the variable menge with the data typ String
    private boolean checked;
    //Declare the variable checked with the data typ boolean
    private long listId;
    //Declare the variable listId with the data typ long

    public Item() {
        this.id = -1L;
        this.name = "";
        this.infos = "";
        this.menge = "";
        this.listId = -1L;
        checked = false;
        //This is the constructor for the Class Item and we initate all the declarations
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

    //Getter for Infos
    public String getInfos() {
        return infos;
    }

    //Setter for infos
    public void setInfos(String infos) {
        this.infos = infos;
    }

    //Getter for menge
    public String getMenge() {
        return menge;
    }

    //Setter for menge
    public void setMenge(String menge) {
        this.menge = menge;
    }

    //Getter for ischecked
    public boolean isChecked() {
        return checked;
    }

    //Setter for checked
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    //Setter for list
    public void setListId(long listId) {
        this.listId = listId;
    }

    //Getter for  the listid
    public long getListId() {
        return listId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return getId() == item.getId();

        //equals the object with boolean and return the right id
    }

    //override the to string with name and menge
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%s)", name, menge);
    }
}
