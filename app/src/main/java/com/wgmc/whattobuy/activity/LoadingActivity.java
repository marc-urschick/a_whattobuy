package com.wgmc.whattobuy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.feature.ShakeSensorFeature;
import com.wgmc.whattobuy.persistence.BuylistOpenHelper;
import com.wgmc.whattobuy.service.FeatureService;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.MainService;
import com.wgmc.whattobuy.service.ShopService;
import com.wgmc.whattobuy.service.ShoplistService;

import static com.wgmc.whattobuy.pojo.Shoptype.ANIMALS;
import static com.wgmc.whattobuy.pojo.Shoptype.BUILDING_SUPPLIES;
import static com.wgmc.whattobuy.pojo.Shoptype.CAFE;
import static com.wgmc.whattobuy.pojo.Shoptype.CHEMICALS;
import static com.wgmc.whattobuy.pojo.Shoptype.CLOTHS;
import static com.wgmc.whattobuy.pojo.Shoptype.COSMETICS;
import static com.wgmc.whattobuy.pojo.Shoptype.DRUGS;
import static com.wgmc.whattobuy.pojo.Shoptype.FURNITURE;
import static com.wgmc.whattobuy.pojo.Shoptype.GIFTS;
import static com.wgmc.whattobuy.pojo.Shoptype.GROCERY;
import static com.wgmc.whattobuy.pojo.Shoptype.LIQUOR;
import static com.wgmc.whattobuy.pojo.Shoptype.LITERATURE;
import static com.wgmc.whattobuy.pojo.Shoptype.MEDIA;
import static com.wgmc.whattobuy.pojo.Shoptype.OFFICE_SUPPLIES;
import static com.wgmc.whattobuy.pojo.Shoptype.OTHER;
import static com.wgmc.whattobuy.pojo.Shoptype.PLANTS;
import static com.wgmc.whattobuy.pojo.Shoptype.RESTAURANT;
import static com.wgmc.whattobuy.pojo.Shoptype.SERVICES;
import static com.wgmc.whattobuy.pojo.Shoptype.TECHNIC;
import static com.wgmc.whattobuy.pojo.Shoptype.TOYS;
import static com.wgmc.whattobuy.pojo.Shoptype.VEHICLES;

public class LoadingActivity extends AppCompatActivity {
    public static final String PREF_NAME = "whattobuy_preferences";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_launcher);

        // init persistence
        BuylistOpenHelper database = new BuylistOpenHelper(this);
        initShoptypeStrings(); // init Strings after Resource Loading
        // Strings are loaded after classes, therefore corresponding translated strings for the enum has to be assigned durig runtime

        // init Services
        MainService.generateInstance();
        try {
            MainService.getInstance().setDb(database);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ShopService.generateInstance();
        ShoplistService.generateInstance();
        ItemService.generateInstance();

        FeatureService.createInstance();

        // init sensors und features
        ShakeSensorFeature ssf = new ShakeSensorFeature(this);
        FeatureService.getInstance().addSensorFeature(ssf);

        // redirect to next page
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MainService.destroyInstance();
    }

    private void initShoptypeStrings() {
        GROCERY.setShownAs(getString(R.string.shoptype_grocery));
        TECHNIC.setShownAs(getString(R.string.shoptype_technic));
        CLOTHS.setShownAs(getString(R.string.shoptype_cloths));
        LIQUOR.setShownAs(getString(R.string.shoptype_liquor));
        FURNITURE.setShownAs(getString(R.string.shoptype_furniture));
        ANIMALS.setShownAs(getString(R.string.shoptype_animals));
        PLANTS.setShownAs(getString(R.string.shoptype_plants));
        LITERATURE.setShownAs(getString(R.string.shoptype_literature));
        OFFICE_SUPPLIES.setShownAs(getString(R.string.shoptype_office));
        TOYS.setShownAs(getString(R.string.shoptype_toys));
        BUILDING_SUPPLIES.setShownAs(getString(R.string.shoptype_building));
        CHEMICALS.setShownAs(getString(R.string.shoptype_chemicals));
        DRUGS.setShownAs(getString(R.string.shoptype_drugs));
        GIFTS.setShownAs(getString(R.string.shoptype_gifts));
        MEDIA.setShownAs(getString(R.string.shoptype_media));
        COSMETICS.setShownAs(getString(R.string.shoptype_cosmetic));
        SERVICES.setShownAs(getString(R.string.shoptype_services));
        VEHICLES.setShownAs(getString(R.string.shoptype_vehicles));
        RESTAURANT.setShownAs(getString(R.string.shoptype_restaurant));
        CAFE.setShownAs(getString(R.string.shoptype_cafe));
        OTHER.setShownAs(getString(R.string.shoptype_other));
    }
}