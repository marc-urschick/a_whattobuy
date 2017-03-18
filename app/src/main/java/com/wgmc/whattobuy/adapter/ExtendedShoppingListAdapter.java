package com.wgmc.whattobuy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShoplistService;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class ExtendedShoppingListAdapter extends ArrayAdapter<ShoppingList> implements Observer {
    private static final DateFormat outFormat = DateFormat.getDateInstance();

    public ExtendedShoppingListAdapter(Context context) {
        super(context, R.layout.list_item_shoplist_extended, ShoplistService.getInstance().getShoppingLists());
        ShoplistService.getInstance().addObserver(this);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_shoplist_extended, parent, false);
        ShoppingList item = getItem(position);

        if (item != null) {
            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_name)).setText(item.getName());
            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_duedate)).setText(outFormat.format(item.getDueTo()));
            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_shopname)).setText(item.getWhereToBuy().getName());

            int cnt = 0, done = 0;

            for (Item it : item.getItems()) {
                cnt++;
                if (it.isChecked()) {
                    done++;
                }
            }

            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_count)).setText(String.format(Locale.getDefault(), "%d of %d done", done, cnt));
        }

        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        notifyDataSetChanged();
    }
}
