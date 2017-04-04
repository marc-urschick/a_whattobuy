package com.wgmc.whattobuy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Locale;
import java.util.Observable;

/**
 * Created by proxie on 21.03.17.
 */

public class MainFragment extends ContentFragment {
    private TextView listsCntTextView, toDoCntTextView, doneCntTextView;

    public MainFragment() {
        addObservingService(ItemService.getInstance());
        addObservingService(ShoplistService.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragement_main, container, false);

        listsCntTextView = ((TextView)v.findViewById(R.id.frag_main_lists_cnt));
        toDoCntTextView = ((TextView)v.findViewById(R.id.frag_main_todo_cnt));
        doneCntTextView = ((TextView)v.findViewById(R.id.frag_main_done_cnt));

        update(null, null);

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
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

        listsCntTextView.setText(numOfLists);
        toDoCntTextView.setText(numOfItems);
        doneCntTextView.setText(numOfDones);
    }
}
