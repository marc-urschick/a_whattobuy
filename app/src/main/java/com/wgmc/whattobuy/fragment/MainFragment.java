package com.wgmc.whattobuy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Locale;

/**
 * Created by proxie on 21.03.17.
 */

public class MainFragment extends ContentFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragement_main, container, false);

        // calculate some values for overview
        int listCnt = 0;
        int openCnt = 0;
        int doneCnt = 0;

        for (ShoppingList l : ShoplistService.getInstance().getShoppingLists()) {
            listCnt++;
            for (Item i : l.getItems()) {
                if (i.isChecked()) {
                    doneCnt++;
                } else {
                    openCnt++;
                }
            }
        }

        String numOfLists = String.format(Locale.getDefault(), "Anzahl der Listen: %d",  listCnt);
        String numOfItems = String.format(Locale.getDefault(), "offene ToDo's: %d",  openCnt);
        String numOfDones = String.format(Locale.getDefault(), "fertiggestellt: %d",  doneCnt);

        ((TextView)v.findViewById(R.id.frag_main_lists_cnt)).setText(numOfLists);
        ((TextView)v.findViewById(R.id.frag_main_todo_cnt)).setText(numOfItems);
        ((TextView)v.findViewById(R.id.frag_main_done_cnt)).setText(numOfDones);

        return v;
    }

    @Override
    public boolean backAction() {
        return true;
    }
}
