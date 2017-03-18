package com.wgmc.whattobuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;

import java.util.List;

/**
 * Created by notxie on 10.03.17.
 */

public class ExtendedShoppingItemListAdapter extends ArrayAdapter<Item> {
    private List<Item> items;

    public ExtendedShoppingItemListAdapter(Context context, List<Item> items) {
        super(context, R.layout.list_item_shoplistitem_extended, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_item_shoplistitem_extended, parent, false);
        final Item item = items.get(position);

        if (item != null) {
            ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_name)).setText(item.getName());
            ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_menge)).setText(item.getMenge());
            ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_infos)).setText(item.getInfos());
            ((CheckBox)v.findViewById(R.id.shoplist_item_extended_list_item_checked)).setChecked(item.isChecked());

            ((CheckBox)v.findViewById(R.id.shoplist_item_extended_list_item_checked)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item.setChecked(((CheckBox) v.findViewById(R.id.shoplist_item_extended_list_item_checked)).isChecked());
//                    ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_name)).set; // todo strike text out
                    // TODO: 10.03.17 update change i guess
                }
            });

            v.findViewById(R.id.shoplist_item_extended_list_item_edit_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo show edit dialog
                }
            });

            v.findViewById(R.id.shoplist_item_extended_list_item_remove_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo remove item
                }
            });
        }

        return v;
    }
}
