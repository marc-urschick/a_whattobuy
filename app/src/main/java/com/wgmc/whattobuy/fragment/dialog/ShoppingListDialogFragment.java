package com.wgmc.whattobuy.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShopService;
import com.wgmc.whattobuy.service.ShoplistService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by proxie on 27.03.17.
 */

public class ShoppingListDialogFragment extends DialogFragment {
    public static ShoppingList list;

    private Spinner shop;
    private EditText name;
    private EditText dueDate;

    public ShoppingListDialogFragment() {
        if (list == null) {
            list = new ShoppingList(-1L);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_shopping_list_edit, container, false);

        shop = (Spinner) v.findViewById(R.id.dialog_shoppinglist_shop);
        name = (EditText) v.findViewById(R.id.dialog_shoppinglist_name);
        dueDate = (EditText) v.findViewById(R.id.dialog_shoppinglist_duedate);

        shop.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ShopService.getInstance().getShops()));

        if (list != null) {
            name.setText(list.getName());
            shop.setSelection(ShopService.getInstance().getShops().indexOf(list.getWhereToBuy()));
            dueDate.setText(list.getDueTo());
        }

        v.findViewById(R.id.dialog_shoppinglist_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (persistInputs()) {
                    list = null;
                    dismiss();
                }
            }
        });

        v.findViewById(R.id.dialog_shoppinglist_abort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = null;
                dismiss();
            }
        });

        return v;
    }

    private boolean persistInputs() {
        String name;
        Shop whereToBuy;

        try {
            name = ShoppingListDialogFragment.this.name.getText().toString();
            whereToBuy = (Shop) shop.getSelectedItem();

            if (name.isEmpty() || whereToBuy == null) {
                throw new Exception();
            }

            list.setName(name);
            list.setDueTo(dueDate.getText().toString());
            list.setWhereToBuy(whereToBuy);

            ShoplistService.getInstance().addShoppingList(list);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Fehler Bei der Eingabe!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
