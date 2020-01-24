package com.appface.akhil.daggerapp.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.appface.akhil.daggerapp.BaseActivity;
import com.appface.akhil.daggerapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Set;

import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity  {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Menu menu;
    private NavigationView navigationDrawerView;
    private AppBarConfiguration appBarConfig;
    private NavController navControllerBottomDrawer;
    private NavController navControllerNavigationDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bttm_nav);
        navigationDrawerView = findViewById(R.id.navigation_drawer_view);

        final Set<Integer> topLevelDestinations = new ArraySet<>();
        topLevelDestinations.add(R.id.scannerScreen);
        topLevelDestinations.add(R.id.foundScreen);
        topLevelDestinations.add(R.id.notFoundScreen);
        topLevelDestinations.add(R.id.settingsScreen);

        appBarConfig = new AppBarConfiguration.Builder(new int[] {R.id.scannerScreen, R.id.foundScreen, R.id.notFoundScreen, R.id.settingsScreen})
                .setDrawerLayout(drawerLayout)
                .build();

        setUpNavigation();
    }

    public void setUpNavigation() {
        navControllerNavigationDrawer = Navigation.findNavController(this, R.id.nav_host_fragment);
        navControllerBottomDrawer = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navControllerNavigationDrawer, appBarConfig);
        NavigationUI.setupWithNavController(bottomNavigationView, navControllerBottomDrawer);
        NavigationUI.setupWithNavController(navigationDrawerView, navControllerNavigationDrawer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;
        initMenuTitles();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout: {
                sessionManager.logOut();
                return true;
            }

            case R.id.scanTime: {
                updateMenuTitles();
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initMenuTitles() {
        MenuItem bedMenuItem = menu.findItem(R.id.scanTime);
        int i = loadSharedPreferences();
        if (i == 2) {
            bedMenuItem.setTitle(R.string.scan_time);
        } else {
            bedMenuItem.setTitle(R.string.scan_time_2);
        }
    }

    private void updateMenuTitles() {
        MenuItem bedMenuItem = menu.findItem(R.id.scanTime);
        if (bedMenuItem.getTitle().equals(getString(R.string.scan_time))) {
            bedMenuItem.setTitle(R.string.scan_time_2);
            storeSharedPreferences(3);
        } else {
            bedMenuItem.setTitle(R.string.scan_time);
            storeSharedPreferences(2);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navControllerBottomDrawer, appBarConfig) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
