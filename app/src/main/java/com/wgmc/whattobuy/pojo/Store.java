package com.wgmc.whattobuy.pojo;

import java.util.Locale;

/**
 * Created by notxie, poidl on 27.01.17. and 10.04.2017.
 */

public class Store {

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

    public Store() {
        this.id = -1L;
        this.name = "";
        this.infos = "";
        this.menge = "";
        //This is the constructor for the Class Store and we initate all the declarations
    }

    public Store(long id, String name, String infos, String menge, boolean checked) {
        this.id = id;
        this.name = name;
        this.infos = infos;
        this.menge = menge;
        this.checked = checked;
        //This is the constructor with values for the Class Store and we initate all the declarations
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

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getMenge() {
        return menge;
    }

    public void setMenge(String menge) {
        this.menge = menge;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2f)", name, menge);
    }

}
