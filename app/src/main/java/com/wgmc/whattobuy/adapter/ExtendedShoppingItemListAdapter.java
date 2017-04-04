package com.wgmc.whattobuy.adapter;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.pwittchen.swipe.library.Swipe;
import com.github.pwittchen.swipe.library.SwipeListener;
import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.dialog.ItemDialogFragment;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ItemService;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 10.03.17.
 */

public class ExtendedShoppingItemListAdapter extends ArrayAdapter<Item> implements Observer {
    private FragmentManager fragmentManager;
    private ShoppingList list;

    public ExtendedShoppingItemListAdapter(Context context, FragmentManager fm, ShoppingList list, List<Item> items) {
        super(context, R.layout.list_item_shoplistitem_extended, items);
        ItemService.getInstance().addObserver(this);
        fragmentManager = fm;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_item_shoplistitem_extended, parent, false);
        final Item item = getItem(position);

        final Swipe sw = new Swipe();

//        v.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                sw.dispatchTouchEvent(event);
//                return true;
//            }
//        });

        sw.addListener(new SwipeListener() {
            @Override
            public void onSwipingLeft(MotionEvent event) {

            }

            @Override
            public void onSwipedLeft(MotionEvent event) {
                editAction(item);
            }

            @Override
            public void onSwipingRight(MotionEvent event) {

            }

            @Override
            public void onSwipedRight(MotionEvent event) {
                System.out.println("swiped right");
            }

            @Override
            public void onSwipingUp(MotionEvent event) {

            }

            @Override
            public void onSwipedUp(MotionEvent event) {

            }

            @Override
            public void onSwipingDown(MotionEvent event) {

            }

            @Override
            public void onSwipedDown(MotionEvent event) {

            }
        });

        if (item != null) {
            CheckBox cb = ((CheckBox)v.findViewById(R.id.shoplist_item_extended_list_item_checked));

            ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_name)).setText(item.getName());
            ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_menge)).setText(item.getMenge());
            ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_infos)).setText(item.getInfos());
            cb.setChecked(item.isChecked());

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item.setChecked(((CheckBox) v.findViewById(R.id.shoplist_item_extended_list_item_checked)).isChecked());
//                    ((TextView)v.findViewById(R.id.shoplist_item_extended_list_item_name)); // todo strike text out
                    // TODO: 10.03.17 update change i guess
                }
            });

            v.findViewById(R.id.shoplist_item_extended_list_item_edit_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editAction(item);
                }
            });

            v.findViewById(R.id.shoplist_item_extended_list_item_remove_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteAction(item);
                }
            });
        }

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        notifyDataSetChanged();
    }

    private void editAction(Item i) {
        DialogFragment d = new ItemDialogFragment(list, i);
        d.show(fragmentManager, "Edit Item");
    }

    private void deleteAction(Item item) {
        ItemService.getInstance().removeItem(item);
    }
}
