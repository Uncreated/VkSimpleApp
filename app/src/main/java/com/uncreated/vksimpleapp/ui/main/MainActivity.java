package com.uncreated.vksimpleapp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.bumptech.glide.request.RequestOptions;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.ActivityMainBinding;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.ui.auth.AuthActivity;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryFragment;
import com.uncreated.vksimpleapp.ui.main.fragments.settings.SettingsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends MvpAppCompatActivity {

    private MainViewModel mainViewModel;

    private NavigationViewHolder navigationViewHolder;
    private Toolbar toolbar;

    private boolean backPressed = false;

    private ActivityMainBinding dataBinding;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setTheme(mainViewModel.getDefaultThemeId());
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navigationViewHolder = new NavigationViewHolder(dataBinding.navView.getHeaderView(0));
        NavigationView.OnNavigationItemSelectedListener selectedListener = getNavigationListener();
        dataBinding.navView.setNavigationItemSelectedListener(selectedListener);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dataBinding.drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dataBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mainViewModel.getGallerySizeLiveData().observe(this, this::setGallerySize);
        mainViewModel.getThemeIdLiveData().observe(this, themeId -> this.changeTheme());
        mainViewModel.getUserLiveData().observe(this, this::setUser);
        mainViewModel.getAuthLiveData().observe(this, auth -> {
            if (auth != null && !auth.isValid()) {
                goAuth();
            }
        });

        if (savedInstanceState == null) {
            initFragment(selectedListener);
        }
    }

    private void initFragment(NavigationView.OnNavigationItemSelectedListener selectedListener) {
        Integer curFragment = dataBinding.getCurrentFragment();
        if (curFragment == null) {
            curFragment = dataBinding.navView.getMenu().getItem(0).getItemId();
        }
        MenuItem menuItem = dataBinding.navView.getMenu().findItem(curFragment);
        menuItem.setChecked(true);
        selectedListener.onNavigationItemSelected(menuItem);
    }

    public void changeTheme() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void goAuth() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    public void setUser(User user) {
        String name = user.getFirstName() + " " + user.getLastName();
        navigationViewHolder.getTextViewName().setText(name);

        GlideApp.with(this)
                .load(user.getPhotoUrl())
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(navigationViewHolder.getImageViewAvatar());
    }

    public void setGallerySize(int size) {
        toolbar.setTitle("Фотографий: " + size);
    }

    @Override
    public void onBackPressed() {
        if (dataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            dataBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (backPressed) {
                finish();
            } else {
                backPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backPressed = false;
                    }
                }, 500);
                Toast.makeText(this, getResources().getString(R.string.tap_twice),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private NavigationView.OnNavigationItemSelectedListener getNavigationListener() {
        return item -> {
            int id = item.getItemId();

            if (id == R.id.nav_gallery) {
                dataBinding.setCurrentFragment(id);
                switchFragment(new GalleryFragment());
            } else if (id == R.id.nav_settings) {
                dataBinding.setCurrentFragment(id);
                switchFragment(new SettingsFragment());
            } else if (id == R.id.nav_logout) {
                mainViewModel.onLogout();
                item.setChecked(false);
            }

            dataBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        };
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, fragment)
                .commit();
    }
}
