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
import com.wgmc.whattobuy.adapter.ExtendedShoppingListAdapter;
import com.wgmc.whattobuy.adapter.StandardShoppingListAdapter;
import com.wgmc.whattobuy.fragment.dialog.ShoppingListDialogFragment;
import com.wgmc.whattobuy.pojo.ShoppingList;

/**
 * Created by notxie on 09.03.17.
 */

public class BuylistListFragment extends Fragment {
    public static final String ARG_EXTENDED_ITEM = "arg_ext_list";
    private BuylistOverviewFragment parent;

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
        final ListAdapter adapter;

//        System.out.println(ext);

        if (ext) {
            adapter = new ExtendedShoppingListAdapter(getActivity());
        } else {
            adapter = new StandardShoppingListAdapter(getActivity());
        }

        ((ListView) v.findViewById(R.id.frag_bll_list)).setAdapter(adapter);
        ((ListView) v.findViewById(R.id.frag_bll_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList list = (ShoppingList) adapter.getItem(i);
                if (list != null) {
//                    ((BuylistOverviewActivity)getActivity()).displayDetailList(list);
//                    BuylistOverviewFragment parent = (BuylistOverviewFragment) getParentFragment();
                    parent.displayDetails(list);
                } else {
                    Toast.makeText(getActivity(), "internal error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        v.findViewById(R.id.frag_bll_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListDialogFragment dialog = new ShoppingListDialogFragment(null);
                dialog.show(getFragmentManager(), "Create Shopping List Dialog");
            }
        });

        return v;
    }

    public void setParent(BuylistOverviewFragment parent) {
        this.parent = parent;
    }
}
