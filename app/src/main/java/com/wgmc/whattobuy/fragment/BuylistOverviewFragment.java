package com.wgmc.whattobuy.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.activity.BuylistOverviewActivity;

/**
 * Created by notxie on 11.03.17.
 */

public class BuylistOverviewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buylist_overview, container, false);
        boolean dual = root.findViewById(R.id.frag_bl_ov_content) == null;
        ((BuylistOverviewActivity)getActivity()).setDualView(dual);

        Bundle listArgs = new Bundle();

        listArgs.putBoolean(BuylistListFragment.ARG_EXTENDED_ITEM, !dual);
        Fragment list = new BuylistListFragment();

        list.setArguments(listArgs);

        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();

        if (dual) {
            transaction.replace(R.id.frag_bl_ov_list, list);
        } else {
            transaction.replace(R.id.frag_bl_ov_content, list);
        }

        transaction.commit();

        return root;
    }
}
