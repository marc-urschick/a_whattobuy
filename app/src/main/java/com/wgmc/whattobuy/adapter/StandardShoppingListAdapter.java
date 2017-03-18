package com.wgmc.whattobuy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 11.03.17.
 */

public class StandardShoppingListAdapter extends ArrayAdapter<ShoppingList> implements Observer {
    public StandardShoppingListAdapter(Context context) {
        super(context, R.layout.list_item_shoplist_standard, ShoplistService.getInstance().getShoppingLists());
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_shoplist_standard, parent, false);
        ShoppingList item = getItem(position);

        if (item != null) {
            ((TextView) v.findViewById(R.id.shoplist_extended_list_item_name)).setText(item.getName());
            ((TextView) v.findViewById(R.id.shoplist_extended_list_item_shopname)).setText(item.getWhereToBuy().getName());
        }

        return v;
    }
}
