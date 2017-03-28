package com.wgmc.whattobuy.activity;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.BuylistListFragment;
import com.wgmc.whattobuy.fragment.BuylistOverviewFragment;
import com.wgmc.whattobuy.fragment.ContentFragment;
import com.wgmc.whattobuy.fragment.MainFragment;
import com.wgmc.whattobuy.fragment.ShopListFragment;

public class MainActivity extends AppCompatActivity {

    private ContentFragment activeFragment;

    private final NavigationView.OnNavigationItemSelectedListener navigationHandler = new NavigationView.OnNavigationItemSelectedListener() {
    // <editor-fold desc="item handling implementation">
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ContentFragment toShow = null;
            System.out.println("item selected");

            switch (item.getItemId()) {
                case R.id.menu_list_buylist:
                    toShow = new BuylistOverviewFragment();
                    toShow.setParent(activeFragment);
                    Bundle args = new Bundle();
                    args.putBoolean(BuylistListFragment.ARG_EXTENDED_ITEM, false);
                    toShow.setArguments(args);
                    break;
                case R.id.menu_list_shop:
                    toShow = new ShopListFragment();
                    toShow.setParent(activeFragment);
                    break;
                case R.id.menu_home:
                    // display home screen as well...
                    toShow = new MainFragment();
                    toShow.setParent(activeFragment);
                    break;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            if (toShow != null)
                displayFragment(toShow);

            return true;
        }
    };
    // </editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_navigation);
        navigationView.setNavigationItemSelectedListener(navigationHandler);

        displayFragment(new MainFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        int backResult = activeFragment.backAction();

        processBackAction(backResult);
    }

    private void processBackAction(int a) {
        switch (a) {
            case ContentFragment.MOVE_TO_PARENT_BACK_ACTION:
                displayFragment(activeFragment.getParent());
                break;
            case ContentFragment.NO_BACK_ACTION:
                return;
            case ContentFragment.REDIRECT_BACK_ACTION:
                super.onBackPressed();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sidemenu, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // react on orientation change and
        // prevent app from crashing when device is rotated
        displayFragment(activeFragment);
    }

    private void displayFragment(ContentFragment fragment) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        if (activeFragment != null)
            trans.remove(activeFragment);
        trans.replace(R.id.activity_main_content_frame, fragment);

        trans.commit();
        activeFragment = fragment;
    }
}
