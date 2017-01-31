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
    GROCERY         (Resources.getSystem().getString(R.string.shoptype_grocery)),
    TECHNIC         (Resources.getSystem().getString(R.string.shoptype_technic)),
    CLOTHS          (Resources.getSystem().getString(R.string.shoptype_cloths)),
    LIQUOR          (Resources.getSystem().getString(R.string.shoptype_liquor)),
    FURNITURE       (Resources.getSystem().getString(R.string.shoptype_furniture)),
    ANIMALS         (Resources.getSystem().getString(R.string.shoptype_animals)),
    PLANTS          (Resources.getSystem().getString(R.string.shoptype_plants)),
    LITERATURE      (Resources.getSystem().getString(R.string.shoptype_literature)),
    OFFICE_SUPPLIES (Resources.getSystem().getString(R.string.shoptype_office)),
    TOYS            (Resources.getSystem().getString(R.string.shoptype_toys)),
    BUILDING_SUPPLIES(Resources.getSystem().getString(R.string.shoptype_building)),
    CHEMICALS       (Resources.getSystem().getString(R.string.shoptype_chemicals)),
    DRUGS           (Resources.getSystem().getString(R.string.shoptype_drugs)),
    GIFTS           (Resources.getSystem().getString(R.string.shoptype_gifts)),
    MEDIA           (Resources.getSystem().getString(R.string.shoptype_media)),
    COSMETICS       (Resources.getSystem().getString(R.string.shoptype_cosmetic)),
    SERVICES        (Resources.getSystem().getString(R.string.shoptype_services)),
    VEHICLES        (Resources.getSystem().getString(R.string.shoptype_vehicles)),
    RESTAURANT      (Resources.getSystem().getString(R.string.shoptype_restaurant)),
    CAFE            (Resources.getSystem().getString(R.string.shoptype_cafe)),
    OTHER           (Resources.getSystem().getString(R.string.shoptype_other));

    private String shownAs;

    Shoptype(String shownAs) {
        this.shownAs = shownAs;
    }

    public String getShownAs() {
        return shownAs;
    }

    public static List<Shoptype> valuesAsList() {
        List<Shoptype> vals = new ArrayList<>();

        Collections.addAll(vals, values());

        return vals;
    }
}
