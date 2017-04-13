package com.wgmc.whattobuy.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.BuylistOverviewFragment;
import com.wgmc.whattobuy.fragment.dialog.ShoppingListDialogFragment;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.ShoplistService;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class ExtendedShoppingListAdapter extends ArrayAdapter<ShoppingList> implements Observer {
    private final Activity host;
    private final BuylistOverviewFragment master;

    public ExtendedShoppingListAdapter(Activity host, BuylistOverviewFragment ovF) {
        super(host, R.layout.list_item_shoplist_extended, ShoplistService.getInstance().getShoppingLists());
        this.host = host;
        this.master = ovF;
        ShoplistService.getInstance().addObserver(this);
        ItemService.getInstance().addObserver(this);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_shoplist_extended, parent, false);
        final ShoppingList item = getItem(position);

        if (item != null) {
            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_name)).setText(item.getName());
            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_duedate)).setText(item.getDueTo());
            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_shopname)).setText(item.getWhereToBuy().getName());

            int all = 0, done = 0;

            for (Item it : item.getItems()) {
                all++;
                if (it.isChecked()) {
                    done++;
                }
            }

            ((TextView) view.findViewById(R.id.shoplist_extended_list_item_count)).setText(String.format(Locale.getDefault(), "%d of %d done", done, all));

            view.findViewById(R.id.shoplist_extended_list_item_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dia = new ShoppingListDialogFragment();
                    ShoppingListDialogFragment.list = item;
                    dia.show(host.getFragmentManager(), "Edit Shopping List Dialog");
                }
            });

            view.findViewById(R.id.shoplist_extended_list_item_remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoplistService.getInstance().removeShoppingList(item);
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    master.displayDetails(item);
                }
            });
        }

        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        notifyDataSetChanged();
    }
}
