package com.wgmc.whattobuy.pojo;

import android.content.res.Resources;

import com.wgmc.whattobuy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by notxie, poidl on 27.01.17. and 10.04.2017.
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

    // Set the diffent enums
    private String shownAs;
    //Declare the variable shownAs with the data typ String
    private long id;
    //Declare the variable id with the data typ long

    Shoptype(long id) {
        this.id = id;
    }

    //Setter for ShownAs
    public void setShownAs(String s) {
        this.shownAs = s + "";
    }

    //Getter for ShownAs
    public String getShownAs() {
        return shownAs;
    }

    //Getter for id
    public long getId() {
        return id;
    }

    //Method to create a List as shoptypes with the value vals and behind the addAll return the List of shoptypes
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
