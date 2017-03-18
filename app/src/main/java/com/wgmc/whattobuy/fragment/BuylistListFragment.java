package com.wgmc.whattobuy.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.activity.BuylistOverviewActivity;
import com.wgmc.whattobuy.adapter.ExtendedShoppingListAdapter;
import com.wgmc.whattobuy.adapter.StandardShoppingListAdapter;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShoplistService;

/**
 * Created by notxie on 09.03.17.
 */

public class BuylistListFragment extends Fragment {
    public static final String ARG_EXTENDED_ITEM = "arg_ext_list";


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
        ListAdapter adapter;

        if (ext) {
            adapter = new ExtendedShoppingListAdapter(getActivity());
        } else {
            adapter = new StandardShoppingListAdapter(getActivity());
        }

        ((ListView) v.findViewById(R.id.frag_bll_list)).setAdapter(adapter);
        ((ListView) v.findViewById(R.id.frag_bll_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList list = (ShoppingList) adapterView.getAdapter().getItem(i);
                if (list != null) {
                    ((BuylistOverviewActivity)getActivity()).displayDetailList(list);
                } else {
                    Toast.makeText(getActivity(), "internal error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
