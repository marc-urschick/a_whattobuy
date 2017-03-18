package com.wgmc.whattobuy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.MainService;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Date;

public class LoadingActivity extends AppCompatActivity {
    private static final String PREF_NAME = "whattobuy_preferences";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        MainService.generateInstance();
//        MainService.getInstance().setPreferences(getSharedPreferences(PREF_NAME, MODE_PRIVATE));
//        List<Store> storeList= new ArrayList<>();
//        storeList.add(new Store(1,"Media Markt","test","10",true));
//        storeList.add(new Store(2,"Cosmos","test","20",true));
//        storeList.add(new Store(3,"Billa","test","50",true));
//
//
//        ListAdapter listAdapter=new ArrayAdapter<Store>(this,android.R.layout.simple_list_item_1,storeList);
//        ListView listView = (ListView) findViewById (R.id.irgendwas);
//        listView.setAdapter(listAdapter);
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_launcher);

        // init persistence

        // init Services
        MainService.generateInstance();
        ShoplistService.generateInstance();

        // dummy data xD
        Item i;

        String[] lists = {"Media Markt", "Billa", "Spar", "Müller", "H & M"};

        for (int li = 0; li < lists.length; li++) {
            ShoppingList list = new ShoppingList(1);
            list.setWhereToBuy(new Shop(14));

            list.setName(lists[li]);

            for (int j = 1; j < 17; j++) {
                i = new Item();
                i.setId(1);
                i.setName("Einkaufsstück " + j);
                i.setChecked(j % 4 == 0);
                i.setInfos("Item no" + j + ",\nItem is in List: " + lists[li]);
                i.setMenge("1 Stück");

                list.addItem(i);
            }

            ShoplistService.getInstance().addShoppingList(list);
        }

        // redirect to next page
        startActivity(new Intent(this, MainActivity.class));
//        startActivity(new Intent(this, BuylistOverviewActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainService.destroyInstance();
    }
}