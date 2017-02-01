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
    GROCERY         (Resources.getSystem().getString(R.string.shoptype_grocery), 1),
    TECHNIC         (Resources.getSystem().getString(R.string.shoptype_technic), 2),
    CLOTHS          (Resources.getSystem().getString(R.string.shoptype_cloths), 3),
    LIQUOR          (Resources.getSystem().getString(R.string.shoptype_liquor), 4),
    FURNITURE       (Resources.getSystem().getString(R.string.shoptype_furniture), 5),
    ANIMALS         (Resources.getSystem().getString(R.string.shoptype_animals), 6),
    PLANTS          (Resources.getSystem().getString(R.string.shoptype_plants), 7),
    LITERATURE      (Resources.getSystem().getString(R.string.shoptype_literature), 8),
    OFFICE_SUPPLIES (Resources.getSystem().getString(R.string.shoptype_office), 9),
    TOYS            (Resources.getSystem().getString(R.string.shoptype_toys), 10),
    BUILDING_SUPPLIES(Resources.getSystem().getString(R.string.shoptype_building), 11),
    CHEMICALS       (Resources.getSystem().getString(R.string.shoptype_chemicals), 12),
    DRUGS           (Resources.getSystem().getString(R.string.shoptype_drugs), 13),
    GIFTS           (Resources.getSystem().getString(R.string.shoptype_gifts), 14),
    MEDIA           (Resources.getSystem().getString(R.string.shoptype_media), 15),
    COSMETICS       (Resources.getSystem().getString(R.string.shoptype_cosmetic), 16),
    SERVICES        (Resources.getSystem().getString(R.string.shoptype_services), 17),
    VEHICLES        (Resources.getSystem().getString(R.string.shoptype_vehicles), 18),
    RESTAURANT      (Resources.getSystem().getString(R.string.shoptype_restaurant), 19),
    CAFE            (Resources.getSystem().getString(R.string.shoptype_cafe), 20),
    OTHER           (Resources.getSystem().getString(R.string.shoptype_other), 21);

    private String shownAs;
    private long id;

    Shoptype(String shownAs, long id) {
        this.shownAs = shownAs;
        this.id = id;
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
        for (Shoptype t:values()) {
            if (t.id == id)
                return t;
        }
        return null;
    }
}
