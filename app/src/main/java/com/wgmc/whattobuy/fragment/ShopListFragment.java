package com.wgmc.whattobuy.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.dialog.ShopEditDialogFragment;
import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.service.ShopService;

import java.util.Observable;

/**
 * Created by proxie on 27.03.17.
 */

public class ShopListFragment extends ContentFragment {
    private ListView list;

    public ShopListFragment() {
        addObservingService(ShopService.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_list, container, false);

        list = ((ListView)v.findViewById(R.id.frag_shl_list));

        list.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ShopService.getInstance().getShops()));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop toEdit = (Shop) parent.getItemAtPosition(position);
                if (toEdit != null) {
                    DialogFragment dia = new ShopEditDialogFragment();
                    ShopEditDialogFragment.heldItem = toEdit;
                    dia.show(getFragmentManager(), "Edit Shop");
                }
            }
        });

        v.findViewById(R.id.frag_shl_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dia = new ShopEditDialogFragment();
                dia.show(getFragmentManager(), "Create Shop");
            }
        });

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (list != null) {
            ((ArrayAdapter) list.getAdapter()).notifyDataSetChanged();
        }
    }
}
