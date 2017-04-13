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
import com.wgmc.whattobuy.feature.ShakeSensorFeature;
import com.wgmc.whattobuy.fragment.BuylistListFragment;
import com.wgmc.whattobuy.fragment.BuylistOverviewFragment;
import com.wgmc.whattobuy.fragment.ContentFragment;
import com.wgmc.whattobuy.fragment.MainFragment;
import com.wgmc.whattobuy.fragment.SettingsFragment;
import com.wgmc.whattobuy.fragment.ShopListFragment;
import com.wgmc.whattobuy.fragment.dialog.BeginTooltipDialogFragment;
import com.wgmc.whattobuy.persistence.BuylistOpenHelper;
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

import static com.wgmc.whattobuy.pojo.Shoptype.ANIMALS;
import static com.wgmc.whattobuy.pojo.Shoptype.BUILDING_SUPPLIES;
import static com.wgmc.whattobuy.pojo.Shoptype.CAFE;
import static com.wgmc.whattobuy.pojo.Shoptype.CHEMICALS;
import static com.wgmc.whattobuy.pojo.Shoptype.CLOTHS;
import static com.wgmc.whattobuy.pojo.Shoptype.COSMETICS;
import static com.wgmc.whattobuy.pojo.Shoptype.DRUGS;
import static com.wgmc.whattobuy.pojo.Shoptype.FURNITURE;
import static com.wgmc.whattobuy.pojo.Shoptype.GIFTS;
import static com.wgmc.whattobuy.pojo.Shoptype.GROCERY;
import static com.wgmc.whattobuy.pojo.Shoptype.LIQUOR;
import static com.wgmc.whattobuy.pojo.Shoptype.LITERATURE;
import static com.wgmc.whattobuy.pojo.Shoptype.MEDIA;
import static com.wgmc.whattobuy.pojo.Shoptype.OFFICE_SUPPLIES;
import static com.wgmc.whattobuy.pojo.Shoptype.OTHER;
import static com.wgmc.whattobuy.pojo.Shoptype.PLANTS;
import static com.wgmc.whattobuy.pojo.Shoptype.RESTAURANT;
import static com.wgmc.whattobuy.pojo.Shoptype.SERVICES;
import static com.wgmc.whattobuy.pojo.Shoptype.TECHNIC;
import static com.wgmc.whattobuy.pojo.Shoptype.TOYS;
import static com.wgmc.whattobuy.pojo.Shoptype.VEHICLES;

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

    /**
     *
     * @param key the key of the preference
     * @return true if the preference is already in the settings management or if it needs to be created
     */
    private boolean settingExists(String key) {
        return SettingsService.getInstance().getSettings().containsKey(key);
    }

    /**
     * create settings and put them into the settings management if they
     * do not exist
     * they might not exist if the app has been updated or newly installed
     */
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
        // init persistence
        BuylistOpenHelper database = new BuylistOpenHelper(this);
        initShoptypeStrings(); // init Strings after Resource Loading

        // init Services
        /// main service on which all other services rely on
        MainService.getInstance();
        try {
            //// set persistence of main service
            MainService.getInstance().setDb(database);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /// init settings service
        SettingsService.getInstance();

        /// init pojo services
        ShopService.getInstance();
        ShoplistService.getInstance();
        ItemService.getInstance();

        /// init feature managment
        FeatureService.getInstance();

        //// init sensors und features
        ShakeSensorFeature ssf = new ShakeSensorFeature(this);
        FeatureService.getInstance().addSensorFeature(ssf);

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

        // set action for menu hamburger to open the menu drawer
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

        // call super method
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        // close drawer when opened
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        // delegating the back action to the fragment (the fragment might not want the activity
        // to handle the back action
        int backResult = QueueHolder.viewingFragment.backAction();
        // after getting the information who needs to do what the action is processed
        processBackAction(backResult);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register features for working actions and functionality
        if (FeatureService.getInstance() != null)
            FeatureService.getInstance().registerSensorFeatures();

        // load settings for integrity
        SettingsService.getInstance().loadSettings(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister features so they don't do stuff in background they shouldn't do
        if (FeatureService.getInstance() != null) {
            FeatureService.getInstance().unregisterSensorFeatures();
        }

        // save settings for resuming again
        SettingsService.getInstance().saveSettings(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *
     * @param a Type defined in ContentFragment which specifies what the processing should do
     */
    private void processBackAction(int a) {
        // when the activity has nothing to do just return back and do nothing
        if (a == ContentFragment.NO_BACK_ACTION)
            return;

        // otherwise move back to the previous view if present
        // if view not present (is the first one) then the activity is closed (parent back
        // action is doing basically the same)
        if (QueueHolder.contentQueue.empty()) {
            super.onBackPressed();
            return;
        }

        // if present move back
        QueueHolder.contentQueue.pop();

        // again if no parent is present then do the same
        if (!QueueHolder.contentQueue.empty()) {
            // get the parent view and try to display it
            ContentFragment tmp = QueueHolder.contentQueue.peek();
            // if null it is not present (...)
            if (tmp == null) {
                super.onBackPressed();

            // if present just display it
            } else {
                displayFragment(tmp);
            }
        } else {
            // there was no parent so delegate to super...
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

    /**
     * Create a new instance (for integrity) of the given Fragment (content fragment)
     * and replace the existing content on the screen with the new one that shall be displayed
     * @param fragment FragmentClass that should be displayed
     */
    private void displayFragment(ContentFragment fragment) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        ContentFragment t = createNewFromClass(fragment, fragment.getArguments());

        if (QueueHolder.viewingFragment != null)
            trans.remove(getActFragmentFromQueue());

        QueueHolder.viewingFragment = t;
        trans.replace(R.id.activity_main_content_frame, getActFragmentFromQueue());

        trans.commit();
    }

    /**
     *
     * @return return actual fragment that is basically topmost on the content queue
     */
    private Fragment getActFragmentFromQueue() {
        return QueueHolder.viewingFragment instanceof SettingsFragment ?
                ((SettingsFragment) QueueHolder.viewingFragment) :
                QueueHolder.viewingFragment;
    }

    /**
     *
     * @param clazzToCreateFrom class which new content fragment shall be instance of
     * @param argsForNewFragment arguments for the fragment if needed
     * @return new instance of the same fragment
     */
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

    /**
     * delegating feature notificaton (when a feature has some update to be processed) to this class
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof FeatureService) {
            if (QueueHolder.viewingFragment instanceof BuylistOverviewFragment) {
                QueueHolder.viewingFragment.update(o, arg);
            }
        }
    }

    // Strings are loaded after classes, therefore corresponding translated
    //   strings for the enum has to be assigned during runtime
    private void initShoptypeStrings() {
        GROCERY.setShownAs(getString(R.string.shoptype_grocery));
        TECHNIC.setShownAs(getString(R.string.shoptype_technic));
        CLOTHS.setShownAs(getString(R.string.shoptype_cloths));
        LIQUOR.setShownAs(getString(R.string.shoptype_liquor));
        FURNITURE.setShownAs(getString(R.string.shoptype_furniture));
        ANIMALS.setShownAs(getString(R.string.shoptype_animals));
        PLANTS.setShownAs(getString(R.string.shoptype_plants));
        LITERATURE.setShownAs(getString(R.string.shoptype_literature));
        OFFICE_SUPPLIES.setShownAs(getString(R.string.shoptype_office));
        TOYS.setShownAs(getString(R.string.shoptype_toys));
        BUILDING_SUPPLIES.setShownAs(getString(R.string.shoptype_building));
        CHEMICALS.setShownAs(getString(R.string.shoptype_chemicals));
        DRUGS.setShownAs(getString(R.string.shoptype_drugs));
        GIFTS.setShownAs(getString(R.string.shoptype_gifts));
        MEDIA.setShownAs(getString(R.string.shoptype_media));
        COSMETICS.setShownAs(getString(R.string.shoptype_cosmetic));
        SERVICES.setShownAs(getString(R.string.shoptype_services));
        VEHICLES.setShownAs(getString(R.string.shoptype_vehicles));
        RESTAURANT.setShownAs(getString(R.string.shoptype_restaurant));
        CAFE.setShownAs(getString(R.string.shoptype_cafe));
        OTHER.setShownAs(getString(R.string.shoptype_other));
    }
}
