package com.wgmc.whattobuy.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.pojo.Shoptype;
import com.wgmc.whattobuy.service.ShopService;

/**
 * Created by proxie on 27.03.17.
 */

public class ShopEditDialogFragment extends DialogFragment {
    public static Shop heldItem;

    private EditText name, address;
    private Spinner type;

    public ShopEditDialogFragment() {

        if (heldItem == null) {
            heldItem = new Shop(-1L);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_shop_edit, container, false);

        name = (EditText) v.findViewById(R.id.dialog_shop_name);
        address = (EditText) v.findViewById(R.id.dialog_shop_address);
        type = (Spinner) v.findViewById(R.id.dialog_shop_type);

        type.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Shoptype.valuesAsList()));

        if (heldItem != null) {
            name.setText(heldItem.getName());
            address.setText(heldItem.getAddress());
        }

        v.findViewById(R.id.dialog_shop_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heldItem.setName(name.getText().toString());
                heldItem.setAddress(address.getText().toString());
                heldItem.setType((Shoptype) type.getSelectedItem());

                ShopService.getInstance().addShop(heldItem);
                heldItem = null;
                dismiss();
            }
        });

        v.findViewById(R.id.dialog_shop_abort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heldItem = null;
                dismiss();
            }
        });

        v.findViewById(R.id.dialog_shop_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heldItem = null;
                ShopService.getInstance().removeShop(heldItem);
            }
        });

        return v;
    }
}
