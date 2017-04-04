package com.wgmc.whattobuy.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ItemService;

/**
 * Created by notxie on 09.03.17.
 */

public class ItemDialogFragment extends DialogFragment {
    private ShoppingList list;
    private Item toEdit;

    public ItemDialogFragment(ShoppingList list, Item toEdit) {
        this.list = list;
        this.toEdit = toEdit;

        if (this.toEdit == null) {
            this.toEdit = new Item();
            this.toEdit.setId(-1);
            this.toEdit.setListId(list.getId());
            this.toEdit.setChecked(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_fragment_item_edit, container, false);

        if (toEdit != null) {
            ((EditText) v.findViewById(R.id.dialog_item_name)).setText(toEdit.getName());
            ((EditText) v.findViewById(R.id.dialog_item_menge)).setText(toEdit.getMenge());
            ((EditText) v.findViewById(R.id.dialog_item_infos)).setText(toEdit.getInfos());

            v.findViewById(R.id.dialog_item_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vv) {
                    toEdit.setName(((EditText) v.findViewById(R.id.dialog_item_name)).getText().toString());
                    toEdit.setInfos(((EditText) v.findViewById(R.id.dialog_item_infos)).getText().toString());
                    toEdit.setMenge(((EditText) v.findViewById(R.id.dialog_item_menge)).getText().toString());


                    boolean newItem = toEdit.getId() < 0;
                    ItemService.getInstance().addItem(toEdit);

                    if (newItem) {
                        list.addItem(toEdit);
                    }
                    dismiss();
                }
            });
        }

        v.findViewById(R.id.dialog_item_abort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}
