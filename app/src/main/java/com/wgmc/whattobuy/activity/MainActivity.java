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
import com.wgmc.whattobuy.fragment.SettingsFragment;
import com.wgmc.whattobuy.fragment.ShopListFragment;
import com.wgmc.whattobuy.service.FeatureService;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements Observer {
    private final NavigationView.OnNavigationItemSelectedListener navigationHandler = new NavigationView.OnNavigationItemSelectedListener() {
    // <editor-fold desc="item handling implementation">
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ContentFragment toShow = null;

            switch (item.getItemId()) {
                case R.id.menu_list_buylist:
                    // Display the actual shopping list content
                    // with master detail view
                    if (!(QueueHolder.viewingFragment instanceof BuylistOverviewFragment)) {
                        toShow = new BuylistOverviewFragment();
                    }
                    break;
                case R.id.menu_list_shop:
                    // Display Overview of Shops
                    if (!(QueueHolder.viewingFragment instanceof ShopListFragment)) {
                        toShow = new ShopListFragment();
                    }
                    break;
                case R.id.menu_home:
                    // display home screen as well...
                    if (!(QueueHolder.viewingFragment instanceof MainFragment)) {
                        toShow = new MainFragment();
                    }
                    break;
                case R.id.menu_settings:
                    // display settings
                    if (!(QueueHolder.viewingFragment instanceof SettingsFragment)) {
                        toShow = new SettingsFragment();
                    }
                    break;
                case R.id.menu_exit:
                    finish();
                    break;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            if (toShow != null) {
                QueueHolder.contentQueue.push(toShow);
                displayFragment(toShow);
            }

            return true;
        }
    };
    // </editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeatureService.getInstance().addObserver(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_navigation);
        navigationView.setNavigationItemSelectedListener(navigationHandler);

        QueueHolder.contentQueue.push(QueueHolder.viewingFragment);
        displayFragment(QueueHolder.viewingFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        int backResult = QueueHolder.viewingFragment.backAction();
        processBackAction(backResult);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FeatureService.getInstance().registerSensorFeatures();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FeatureService.getInstance().unregisterSensorFeatures();
    }

    private void processBackAction(int a) {
        if (a == ContentFragment.NO_BACK_ACTION)
            return;

        if (QueueHolder.contentQueue.empty()) {
            super.onBackPressed();
            return;
        }

        QueueHolder.contentQueue.pop();
        if (!QueueHolder.contentQueue.empty()) {
            ContentFragment tmp = QueueHolder.contentQueue.peek();
            if (tmp == null) {
                super.onBackPressed();
            } else {
                displayFragment(tmp);
            }
        } else {
            super.onBackPressed();
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
        displayFragment(QueueHolder.viewingFragment);
    }

    private void displayFragment(ContentFragment fragment) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        ContentFragment t = createNewFromClass(fragment, fragment.getArguments());

        if (QueueHolder.viewingFragment != null)
            trans.remove(QueueHolder.viewingFragment);
        trans.replace(R.id.activity_main_content_frame, t);

        trans.commit();
        QueueHolder.viewingFragment = t;
    }

    private ContentFragment createNewFromClass(ContentFragment clazzToCreateFrom, Bundle argsForNewFragment) {
        ContentFragment newInst;

        if (clazzToCreateFrom.getClass().equals(MainFragment.class)) {
            newInst = new MainFragment();
        } else if (clazzToCreateFrom.getClass().equals(BuylistOverviewFragment.class)) {
            newInst = new BuylistOverviewFragment();
        } else if (clazzToCreateFrom.getClass().equals(ShopListFragment.class)) {
            newInst = new ShopListFragment();
        } else {
            throw new IllegalAccessError();
        }

        newInst.setArguments(argsForNewFragment);
        return newInst;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof FeatureService) {
            if (QueueHolder.viewingFragment instanceof BuylistOverviewFragment) {
                QueueHolder.viewingFragment.update(o, arg);
            }
        }
    }
}
