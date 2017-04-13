package com.wgmc.whattobuy.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.ShoplistService;

/**
 * Created by notxie on 09.03.17.
 */
// Dialog for editing and creating Items and handing them over to the service class
public class ItemDialogFragment extends DialogFragment {
    // static instance holding of Item pojo that shall be edited or created
    public static Item item;

    public ItemDialogFragment() {
        super();

        if (item == null) {
            item = new Item();
            item.setChecked(false);
            item.setId(-1L);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_fragment_item_edit, container, false);

        if (item != null) {
            ((EditText) v.findViewById(R.id.dialog_item_name)).setText(item.getName());
            ((EditText) v.findViewById(R.id.dialog_item_menge)).setText(item.getMenge());
            ((EditText) v.findViewById(R.id.dialog_item_infos)).setText(item.getInfos());

            v.findViewById(R.id.dialog_item_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vv) {
                    item.setName(((EditText) v.findViewById(R.id.dialog_item_name)).getText().toString());
                    item.setInfos(((EditText) v.findViewById(R.id.dialog_item_infos)).getText().toString());
                    item.setMenge(((EditText) v.findViewById(R.id.dialog_item_menge)).getText().toString());


                    boolean newItem = item.getId() < 0;
                    ItemService.getInstance().addItem(item);

                    if (newItem) {
                        ShoplistService
                                .getInstance()
                                .getShoppingListById((int) item.getListId())
                                .addItem(item);
                    }
                    item = null;
                    dismiss();
                }
            });
        }

        v.findViewById(R.id.dialog_item_abort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = null;
                dismiss();
            }
        });

        v.findViewById(R.id.dialog_item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ItemService.getInstance().removeItem(item);
                    item = null;
                    dismiss();
                } catch (Exception e) {
                    Log.e(ItemDialogFragment.this.getClass().getSimpleName(), "Exception while deleting item!", e);
                    Toast.makeText(getActivity(), "An error occurred. Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}
