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
import com.wgmc.whattobuy.pojo.ShoppingList;

/**
 * Created by notxie on 11.03.17.
 */

public class BuylistOverviewFragment extends ContentFragment {
    private boolean dual;
    private boolean detailsOpen = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buylist_overview, container, false);
        dual = root.findViewById(R.id.frag_bl_ov_content) == null;
//        boolean dual = false;

        Bundle listArgs = new Bundle();

        listArgs.putBoolean(BuylistListFragment.ARG_EXTENDED_ITEM, !dual);
        Fragment list = new BuylistListFragment(this);

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

    public void displayDetails(ShoppingList list) {
        System.out.println("display list");
        Bundle arg = new Bundle();
        arg.putLong(BuylistDetailFragment.ARG_LIST_ID, list.getId());
        System.out.println("bundle created and filled");
        Fragment frag = new BuylistDetailFragment();
        frag.setArguments(arg);
        System.out.println("fragment created and args assigned");
        detailsOpen = true;
        getFragmentManager().beginTransaction().replace(dual ? R.id.frag_bl_ov_detail : R.id.frag_bl_ov_content, frag).commit();
        System.out.println("fragment switched");
    }

    @Override
    public boolean backAction() {
        if (detailsOpen) {
            if (!dual) {
                Bundle listArgs = new Bundle();

                listArgs.putBoolean(BuylistListFragment.ARG_EXTENDED_ITEM, !dual);
                Fragment list = new BuylistListFragment(this);

                list.setArguments(listArgs);

                getFragmentManager().beginTransaction().replace(R.id.frag_bl_ov_content, list).commit();
            }

            detailsOpen = false;
            return false;
        }
        return true;
    }
}
