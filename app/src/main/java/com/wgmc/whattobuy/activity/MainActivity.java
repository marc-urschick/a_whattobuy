package com.wgmc.whattobuy.activity;

import android.app.Fragment;
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
import android.view.View;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.BuylistListFragment;
import com.wgmc.whattobuy.fragment.BuylistOverviewFragment;
import com.wgmc.whattobuy.fragment.ContentFragment;
import com.wgmc.whattobuy.fragment.MainFragment;
import com.wgmc.whattobuy.fragment.SettingsFragment;
import com.wgmc.whattobuy.fragment.ShopListFragment;
import com.wgmc.whattobuy.fragment.dialog.BeginTooltipDialogFragment;
import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.service.FeatureService;
import com.wgmc.whattobuy.service.ItemService;
import com.wgmc.whattobuy.service.MainService;
import com.wgmc.whattobuy.service.SettingsService;
import com.wgmc.whattobuy.service.ShopService;
import com.wgmc.whattobuy.service.ShoplistService;

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

    private boolean settingExists(String key) {
        return SettingsService.getInstance().getSettings().containsKey(key);
    }

    private void installNonExistentSettings() {
        if (!settingExists(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS)) {
            SettingsService.getInstance()
                    .putSetting(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS, false);
        }

        if (!settingExists(SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL)) {
            SettingsService.getInstance()
                    .putSetting(SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL, true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load settings from saved state
        SettingsService.getInstance().initSettingKeys(this);
        SettingsService.getInstance().loadSettings(this);
        // create all settings if not present (after update or first install or so)
        installNonExistentSettings();

        // set content to main engine file
        setContentView(R.layout.activity_main);

        // add this activity to observe the features
        FeatureService.getInstance().addObserver(this);

        // find navigation sidebar (sidemenu that slides open) and assign action handling
        final NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_navigation);
        navigationView.setNavigationItemSelectedListener(navigationHandler);

        findViewById(R.id.activity_main_hamburger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        // push opening fragment to queueing list and display it
        QueueHolder.contentQueue.push(QueueHolder.viewingFragment);
        displayFragment(QueueHolder.viewingFragment);

        // show welcome screen (tooltips) if enabled
        if (SettingsService.getInstance().getSetting(SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL).equals("true")) {
            new BeginTooltipDialogFragment().show(getFragmentManager(), "tooltips");
        }
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
        if (FeatureService.getInstance() != null)
            FeatureService.getInstance().registerSensorFeatures();
        SettingsService.getInstance().loadSettings(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (FeatureService.getInstance() != null) {
            FeatureService.getInstance().unregisterSensorFeatures();
        }

        SettingsService.getInstance().saveSettings(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FeatureService.destroyInstance();
        ItemService.destroyInstance();
        ShoplistService.destroyInstance();
        ShopService.destroyInstance();
        MainService.destroyInstance();
        SettingsService.destroyInstance();
    }

    @Override
    public void onDestroy() {
        FragmentHolder.LIST_FRAGMENT = null;
        FragmentHolder.DETAIL_FRAGMENT = null;
        QueueHolder.viewingFragment = new MainFragment();
        QueueHolder.contentQueue.clear();
        super.onDestroy();
    }

    /**
     *
     * @param a Type defined in ContentFragment which specifies what the processing should do
     */
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
            trans.remove(getActFragmentFromQueue());

        QueueHolder.viewingFragment = t;
        trans.replace(R.id.activity_main_content_frame, getActFragmentFromQueue());

        trans.commit();
    }

    private Fragment getActFragmentFromQueue() {
        return QueueHolder.viewingFragment instanceof SettingsFragment ?
                ((SettingsFragment) QueueHolder.viewingFragment) :
                QueueHolder.viewingFragment;
    }

    private ContentFragment createNewFromClass(ContentFragment clazzToCreateFrom, Bundle argsForNewFragment) {
        ContentFragment newInst;

        if (clazzToCreateFrom.getClass().equals(MainFragment.class)) {
            newInst = new MainFragment();
        } else if (clazzToCreateFrom.getClass().equals(BuylistOverviewFragment.class)) {
            newInst = new BuylistOverviewFragment();
        } else if (clazzToCreateFrom.getClass().equals(ShopListFragment.class)) {
            newInst = new ShopListFragment();
        } else if (clazzToCreateFrom.getClass().equals(SettingsFragment.class)) {
            newInst = new SettingsFragment();
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
