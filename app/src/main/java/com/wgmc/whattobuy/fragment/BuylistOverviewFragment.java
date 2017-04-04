package com.wgmc.whattobuy.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.activity.FragmentHolder;
import com.wgmc.whattobuy.pojo.ShoppingList;

import java.util.Observable;

/**
 * Created by notxie on 11.03.17.
 */

public class BuylistOverviewFragment extends ContentFragment {
    private boolean dual;

    private FragmentManager fm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buylist_overview, container, false);
        dual = root.findViewById(R.id.frag_bl_ov_detail) != null;

//        if (FragmentHolder.LIST_FRAGMENT == null) {
            Bundle listArgs = new Bundle();
            listArgs.putBoolean(BuylistListFragment.ARG_EXTENDED_ITEM, !dual);
            FragmentHolder.LIST_FRAGMENT = new BuylistListFragment();
            FragmentHolder.LIST_FRAGMENT.setParent(this);
            FragmentHolder.LIST_FRAGMENT.setArguments(listArgs);
//        }

        if (dual) {
            fm.beginTransaction().replace(R.id.frag_bl_ov_list, FragmentHolder.LIST_FRAGMENT).commit();
            if (FragmentHolder.DETAIL_FRAGMENT != null) {
                fm.beginTransaction().replace(R.id.frag_bl_ov_detail, FragmentHolder.DETAIL_FRAGMENT).commit();
            }
        } else {
            if (FragmentHolder.DETAIL_FRAGMENT != null) {
                fm.beginTransaction().replace(R.id.frag_bl_ov_list, FragmentHolder.DETAIL_FRAGMENT).commit();
            } else {
                fm.beginTransaction().replace(R.id.frag_bl_ov_list, FragmentHolder.LIST_FRAGMENT).commit();
            }
        }

        return root;
    }

    public void displayDetails(ShoppingList list) {
        Bundle arg = new Bundle();
        arg.putLong(BuylistDetailFragment.ARG_LIST_ID, list.getId());
        FragmentHolder.DETAIL_FRAGMENT = new BuylistDetailFragment();
        FragmentHolder.DETAIL_FRAGMENT.setArguments(arg);
        fm.beginTransaction().replace(dual ? R.id.frag_bl_ov_detail : R.id.frag_bl_ov_list, FragmentHolder.DETAIL_FRAGMENT).commit();
    }

    @Override
    public int backAction() {
        if (FragmentHolder.DETAIL_FRAGMENT != null) {
            if (!dual) {
                fm.beginTransaction().replace(R.id.frag_bl_ov_list, FragmentHolder.LIST_FRAGMENT).commit();
            } else {
                fm.beginTransaction().remove(FragmentHolder.DETAIL_FRAGMENT).commit();
            }

            FragmentHolder.DETAIL_FRAGMENT = null;
            return NO_BACK_ACTION;
        }
        return MOVE_TO_PARENT_BACK_ACTION;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
