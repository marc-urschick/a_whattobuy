package com.wgmc.whattobuy.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.BuylistDetailFragment;
import com.wgmc.whattobuy.fragment.BuylistListFragment;
import com.wgmc.whattobuy.fragment.BuylistOverviewFragment;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.service.ShoplistService;

import java.util.Observable;
import java.util.Observer;

public class BuylistOverviewActivity extends AppCompatActivity {
    private boolean dualView;
    private boolean contentOpen;

    private BuylistOverviewFragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentFragment = new BuylistOverviewFragment();
        getFragmentManager().beginTransaction().replace(R.id.activity_main_content_frame, contentFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.activity_main_content_frame, contentFragment).commit();
    }

    public void displayDetailList(ShoppingList list) {
//        contentFragment.showContent(list);
//        contentOpen = true;

        if (!dualView) {
            Intent i = new Intent(this, BuylistDetailActivity.class);
            i.putExtra(BuylistDetailFragment.ARG_LIST_ID, list.getId());
            startActivity(i);
        } else {
            Fragment detail = new BuylistDetailFragment();
            Bundle args = new Bundle();
            args.putLong(BuylistDetailFragment.ARG_LIST_ID, list.getId());
            detail.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.frag_bl_ov_detail, detail).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (dualView) {
            super.onBackPressed();
        } else {
            if (contentOpen) {
//                contentFragment.hideContent();
                contentOpen = false;
            } else {
                super.onBackPressed();
            }
        }
    }

    public boolean isDualView() {
        return dualView;
    }

    public void setDualView(boolean dualView) {
        this.dualView = dualView;
    }
}
