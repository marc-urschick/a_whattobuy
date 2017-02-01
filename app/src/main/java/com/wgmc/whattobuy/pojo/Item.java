package com.wgmc.whattobuy.pojo;

import java.util.Locale;

/**
 * Created by notxie on 27.01.17.
 */

public class Item {
    private long id;
    private String name;
    private String infos;
    private double menge;

    public Item() {
        this.id = -1L;
        this.name = "";
        this.infos = "";
        this.menge = 0.0;
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

    public double getMenge() {
        return menge;
    }

    public void setMenge(double menge) {
        this.menge = menge;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2f)", name, menge);
    }
}
