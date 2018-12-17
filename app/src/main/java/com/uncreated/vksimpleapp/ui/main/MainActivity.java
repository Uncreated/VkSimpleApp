package com.uncreated.vksimpleapp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.ActivityMainBinding;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryFragment;
import com.uncreated.vksimpleapp.ui.main.fragments.settings.SettingsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dataBinding;
    private MainViewModel mainViewModel;

    private NavigationViewHolder navigationViewHolder;
    private NavigationView.OnNavigationItemSelectedListener selectedListener;

    private boolean backPressed = false;

    //TODO: save and load to Bundle
    private static Integer curFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setTheme(mainViewModel.getDefaultThemeId());
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(dataBinding.appBar.toolbar);

        initNavigation();

        if (savedInstanceState == null) {
            initFragment();
        }

        observeAll();
    }

    private void initNavigation() {
        navigationViewHolder = new NavigationViewHolder(dataBinding.navView.getHeaderView(0));
        selectedListener = getNavigationListener();
        dataBinding.navView.setNavigationItemSelectedListener(selectedListener);
        initToggle();
    }

    private void initToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dataBinding.drawerLayout,
                dataBinding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dataBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void observeAll() {
        mainViewModel.getGallerySizeLiveData().observe(this, this::setGallerySize);
        mainViewModel.getThemeChangeLiveData().observe(this, o -> this.changeTheme());
        mainViewModel.getUserLiveData().observe(this, this::setUser);
        mainViewModel.getAuthLiveData().observe(this, auth -> {
            if (auth != null && !auth.isValid()) {
                goAuth();
            }
        });
    }

    private void initFragment() {
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
        //startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    //TODO:make nav class and subscribe
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
        dataBinding.appBar.toolbar.setTitle("Фотографий: " + size);
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
                curFragment = id;
                switchFragment(new GalleryFragment());
            } else if (id == R.id.nav_settings) {
                curFragment = id;
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
