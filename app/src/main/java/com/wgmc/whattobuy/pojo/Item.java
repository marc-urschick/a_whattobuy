package com.wgmc.whattobuy.pojo;

import java.util.Locale;

/**
 * Created by notxie on 27.01.17.
 */

public class Item {
    private long id;
    private String name;
    private String infos;
    private String menge;
    private boolean checked;

    public Item() {
        this.id = -1L;
        this.name = "";
        this.infos = "";
        this.menge = "";
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return getId() == item.getId();

    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%s)", name, menge);
    }
}
