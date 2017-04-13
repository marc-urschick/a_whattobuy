package com.wgmc.whattobuy.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.adapter.ExtendedShoppingListAdapter;
import com.wgmc.whattobuy.adapter.StandardShoppingListAdapter;
import com.wgmc.whattobuy.fragment.dialog.ShoppingListDialogFragment;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by notxie on 09.03.17.
 */

public class BuylistListFragment extends ContentFragment implements Observer {
    public static final String ARG_EXTENDED_ITEM = "arg_ext_list";
    private BuylistOverviewFragment parent;
    private ListAdapter adapter;

    public BuylistListFragment() {
        addObservingService(ItemService.getInstance());
        addObservingService(ShoplistService.getInstance());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_buylist_list, container, false);
        Bundle args = getArguments();
        boolean ext = args.getBoolean(ARG_EXTENDED_ITEM, true);

        if (ext) {
            adapter = new ExtendedShoppingListAdapter(getActivity(), parent);
        } else {
            adapter = new StandardShoppingListAdapter(getActivity());
        }

        ((ListView) v.findViewById(R.id.frag_bll_list)).setAdapter(adapter);
        ((ListView) v.findViewById(R.id.frag_bll_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList list = (ShoppingList) adapter.getItem(i);
                if (list != null) {
                    Log.d(getClass().getSimpleName(), "displaying list: " + list.getId());
                    parent.displayDetails(list);
                } else {
                    Toast.makeText(getActivity(), "internal error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        v.findViewById(R.id.frag_bll_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListDialogFragment dialog = new ShoppingListDialogFragment();
                dialog.show(getFragmentManager(), "Create Shopping List Dialog");
            }
        });

        return v;
    }

    public void setParent(BuylistOverviewFragment parent) {
        this.parent = parent;
    }

    @Override
    public void update(Observable o, Object arg) {
        ((ArrayAdapter)adapter).notifyDataSetChanged();
    }
}
