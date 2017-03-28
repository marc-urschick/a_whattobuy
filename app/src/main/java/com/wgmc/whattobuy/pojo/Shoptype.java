package com.wgmc.whattobuy.pojo;

import android.content.res.Resources;

import com.wgmc.whattobuy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by notxie on 27.01.17.
 */

public enum Shoptype {
    GROCERY(1),
    TECHNIC(2),
    CLOTHS(3),
    LIQUOR(4),
    FURNITURE(5),
    ANIMALS(6),
    PLANTS(7),
    LITERATURE(8),
    OFFICE_SUPPLIES(9),
    TOYS(10),
    BUILDING_SUPPLIES(11),
    CHEMICALS(12),
    DRUGS(13),
    GIFTS(14),
    MEDIA(15),
    COSMETICS(16),
    SERVICES(17),
    VEHICLES(18),
    RESTAURANT(19),
    CAFE(20),
    OTHER(21);

    private String shownAs;
    private long id;

    Shoptype(long id) {
        this.id = id;
    }

    public void setShownAs(String s) {
        this.shownAs = s + "";
    }

    public String getShownAs() {
        return shownAs;
    }

    public long getId() {
        return id;
    }

    public static List<Shoptype> valuesAsList() {
        List<Shoptype> vals = new ArrayList<>();

        Collections.addAll(vals, values());

        return vals;
    }

    public static Shoptype decodeFromId(long id) {
        for (Shoptype t : values()) {
            if (t.id == id)
                return t;
        }
        return null;
    }

    @Override
    public String toString() {
        return shownAs;
    }
}
