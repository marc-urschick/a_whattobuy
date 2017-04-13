package com.wgmc.whattobuy.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.adapter.ExtendedShoppingItemListAdapter;
import com.wgmc.whattobuy.fragment.dialog.ItemDialogFragment;
import com.wgmc.whattobuy.fragment.dialog.ShoppingListDialogFragment;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.FeatureService;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.SettingsService;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class BuylistDetailFragment extends ContentFragment implements Observer {
    public static final String ARG_LIST_ID = "arg_list_id";

    private TextView title, date, shop;
    private ListView items;

    private ShoppingList list;

    public BuylistDetailFragment() {
        addObservingService(ShoplistService.getInstance());
        addObservingService(ItemService.getInstance());
        addObservingService(FeatureService.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buylist_detail, container, false);

        title = (TextView) root.findViewById(R.id.frag_bld_title);
        date = (TextView) root.findViewById(R.id.frag_bld_date);
        shop = (TextView) root.findViewById(R.id.frag_bld_shop);
        items = (ListView) root.findViewById(R.id.frag_bld_items);

        list = ShoplistService.getInstance().getShoppingListById((int) getArguments().getLong(ARG_LIST_ID, -1));
        Log.d("Detail Fragment", list == null ? "list is null" : list.toString());

        root.findViewById(R.id.frag_bld_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dia = new ItemDialogFragment(list, null);
                dia.show(getFragmentManager(), "Neues Element erstellen");
            }
        });

        root.findViewById(R.id.frag_bld_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dia = new ShoppingListDialogFragment(list);
                dia.show(getFragmentManager(), "Edit Shopping List Dialog");
            }
        });

        if (list != null) {
            title.setText(list.getName());
            date.setText(ShoplistService.displayDateFormat.format(list.getDueTo()));
            shop.setText(list.getWhereToBuy().getName());
            items.setAdapter(createAdapter());
        }

        return root;
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d(getClass().getSimpleName(), observable + " -> " + o);
        if (o instanceof ShoppingList) {
            list = (ShoppingList) o;

            if (title != null) {
                title.setText(list.getName());
            }

            if (date != null) {
                date.setText(ShoplistService.displayDateFormat.format(list.getDueTo()));
            }

            if (shop != null) {
                shop.setText(list.getWhereToBuy().getName());
            }

            if (items != null) {
                items.setAdapter(createAdapter());
            }
        }

        if (observable instanceof FeatureService) {
            if (list != null && SettingsService.getInstance().getSetting(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS).equals(true)) {
                for (Item i : list.getItems()) {
                    System.out.println("in list");
                    if (!i.isChecked()) {
                        System.out.println("isnt checked");
                        i.setChecked(true);
                        ItemService.getInstance().addItem(i);
                        return;
                    }
                }
                Toast.makeText(getActivity(), "All Items checked already!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ListAdapter createAdapter() {
        return new ExtendedShoppingItemListAdapter(
                getActivity(),
                getFragmentManager(),
                list,
                list.getItems()
        );
    }
}
