package com.wgmc.whattobuy.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.fragment.BuylistListFragment;
import com.wgmc.whattobuy.fragment.BuylistOverviewFragment;
import com.wgmc.whattobuy.fragment.ContentFragment;
import com.wgmc.whattobuy.fragment.MainFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ContentFragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction().add(R.id.activity_main_content_frame, activeFragment = new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (activeFragment != null) {
            if (!(activeFragment instanceof MainFragment)) {
                if (activeFragment.backAction()) {
                    getFragmentManager().beginTransaction().replace(R.id.activity_main_content_frame, activeFragment = new MainFragment()).commit();
                }
            } else {
                super.onBackPressed();
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
        getFragmentManager().beginTransaction().replace(R.id.activity_main_content_frame, activeFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_buylist:
//                startActivity(new Intent(getApplicationContext(), BuylistOverviewActivity.class));
                System.out.println("i kumm no ins on nav item selected hin...");
                FragmentManager man = getFragmentManager();
                BuylistOverviewFragment frag = new BuylistOverviewFragment();
                Bundle args = new Bundle();
                args.putBoolean(BuylistListFragment.ARG_EXTENDED_ITEM, false);
                frag.setArguments(args);
                man.beginTransaction().replace(R.id.activity_main_content_frame, frag).commit();
                activeFragment = frag;
                break;
            case R.id.menu_list_shop:
                break;
            case R.id.menu_home:
                // display home screen as well...
                activeFragment = null;
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
