package com.wgmc.whattobuy.pojo;

import java.util.Locale;

/**
 * Created by Wolfgang on 04.03.2017.
 */

public class Store {

    private long id;
    private String name;
    private String infos;
    private String menge;
    private boolean checked;

    public Store() {
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
    public String toString() {
        return String.format(Locale.getDefault(), "%s (%.2f)", name, menge);
    }

}
