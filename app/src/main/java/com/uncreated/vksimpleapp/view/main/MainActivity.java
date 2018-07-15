package com.uncreated.vksimpleapp.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.request.RequestOptions;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.presenter.main.MainPresenter;
import com.uncreated.vksimpleapp.view.auth.AuthActivity;
import com.uncreated.vksimpleapp.view.main.gallery.GalleryFragment;
import com.uncreated.vksimpleapp.view.main.settings.SettingsFragment;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final String FRAGMENT_INDEX_KEY = "fragmentIndexKey";

    @Inject
    App app;

    @Named("themeId")
    @Inject
    Integer themeId;

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private NavigationViewHolder navigationViewHolder;

    private int curFragment;

    public MainActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        MainPresenter mainPresenter = new MainPresenter();
        app.getAppComponent().inject(mainPresenter);
        return mainPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        navigationViewHolder = new NavigationViewHolder(navigationView.getHeaderView(0));
        NavigationView.OnNavigationItemSelectedListener selectedListener = getNavigationListener();
        navigationView.setNavigationItemSelectedListener(selectedListener);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            curFragment = navigationView.getMenu().getItem(0).getItemId();
            curFragment = getIntent().getIntExtra(FRAGMENT_INDEX_KEY, curFragment);
            MenuItem menuItem = navigationView.getMenu().findItem(curFragment);
            menuItem.setChecked(true);
            selectedListener.onNavigationItemSelected(menuItem);
        } else {
            curFragment = savedInstanceState.getInt(FRAGMENT_INDEX_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(FRAGMENT_INDEX_KEY, curFragment);
    }

    @Override
    public void changeTheme(Integer themeId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(FRAGMENT_INDEX_KEY, curFragment);
        startActivity(intent);
        finish();
    }

    @Override
    public void goAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void setUser(User user) {
        String name = user.getFirstName() + " " + user.getLastName();
        navigationViewHolder.getTextViewName().setText(name);

        GlideApp.with(this)
                .load(user.getPhotoUrl())
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(navigationViewHolder.getImageViewAvatar());
    }

    @Override
    public void setGallerySize(int size) {
        toolbar.setTitle("Фотографий: " + size);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }/* else {
            super.onBackPressed();
        }*/
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
                mainPresenter.onLogout();
                item.setChecked(false);
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        };
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, fragment)
                .commit();
    }
}
