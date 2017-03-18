package com.wgmc.whattobuy.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.adapter.ExtendedShoppingItemListAdapter;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class BuylistDetailFragment extends Fragment implements Observer {
    public static final String ARG_LIST_ID = "arg_list_id";

    private TextView title, date, shop;
    private ListView items;

    private ShoppingList list;

    public BuylistDetailFragment() {
        ShoplistService.getInstance().addObserver(this);
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

        if (list != null) {
            title.setText(list.getName());
            date.setText(ShoplistService.displayDateFormat.format(list.getDueTo()));
            shop.setText(list.getWhereToBuy().getName());
            items.setAdapter(new ExtendedShoppingItemListAdapter(getActivity(), list.getItems()));
            System.out.println("showing list");
        }

        return root;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ShoppingList) {
            System.out.println("displaying " + o);
            list = (ShoppingList) o;
//            this.
            if (title != null) {
                System.out.println("showing list");
                title.setText(list.getName());
            }

            if (date != null) {
                date.setText(ShoplistService.displayDateFormat.format(list.getDueTo()));
            }

            if (shop != null) {
                shop.setText(list.getWhereToBuy().getName());
            }

            if (items != null) {
                items.setAdapter(new ExtendedShoppingItemListAdapter(getActivity(), list.getItems()));
            }
        }
    }
}