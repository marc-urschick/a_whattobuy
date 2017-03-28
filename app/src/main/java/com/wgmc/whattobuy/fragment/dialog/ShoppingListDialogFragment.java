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

import java.util.Calendar;
import java.util.Date;

/**
 * Created by proxie on 27.03.17.
 */

public class ShoppingListDialogFragment extends DialogFragment {

    private Spinner shop;
    private EditText name;
    private DatePicker dueDate;

    private ShoppingList list;

    private Date compareDate;
    private Date minDate;

    public ShoppingListDialogFragment(ShoppingList list) {
        this.list = list;
        if (list == null) {
            this.list = new ShoppingList(-1L);
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        compareDate = c.getTime();

        c.add(Calendar.DAY_OF_MONTH, -1);
        minDate = c.getTime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_shopping_list_edit, container, false);

        shop = (Spinner) v.findViewById(R.id.dialog_shoppinglist_shop);
        name = (EditText) v.findViewById(R.id.dialog_shoppinglist_name);
        dueDate = (DatePicker) v.findViewById(R.id.dialog_shoppinglist_duedate);

        shop.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ShopService.getInstance().getShops()));



        dueDate.setMinDate(minDate.getTime());

        if (list != null) {
            name.setText(list.getName());
            shop.setSelection(ShopService.getInstance().getShops().indexOf(list.getWhereToBuy()));
        }

        v.findViewById(R.id.dialog_shoppinglist_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                Calendar c;
                Shop whereToBuy;

                try {
                    name = ShoppingListDialogFragment.this.name.getText().toString();
                    c = Calendar.getInstance();
                    c.set(
                            dueDate.getYear(),
                            dueDate.getMonth(),
                            dueDate.getDayOfMonth()
                    );
                    whereToBuy = (Shop) shop.getSelectedItem();

                    if (name.isEmpty() || whereToBuy == null) {
                        throw new Exception();
                    }

                    list.setName(name);
                    list.setDueTo(c.getTime());
                    list.setWhereToBuy(whereToBuy);

                    ShoplistService.getInstance().addShoppingList(list);
                    dismiss();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Fehler Bei der Eingabe!", Toast.LENGTH_LONG).show();
                }
            }
        });

        v.findViewById(R.id.dialog_shoppinglist_abort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}
