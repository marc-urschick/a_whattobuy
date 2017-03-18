package com.wgmc.whattobuy.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.BuylistDetailFragment;

/**
 * Created by notxie on 11.03.17.
 */

public class BuylistDetailActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment f = new BuylistDetailFragment();
        Bundle args = new Bundle();

        args.putLong(BuylistDetailFragment.ARG_LIST_ID, getIntent().getLongExtra(BuylistDetailFragment.ARG_LIST_ID, -1));
        f.setArguments(args);

        getFragmentManager().beginTransaction().replace(R.id.activity_main_content_frame, f).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
