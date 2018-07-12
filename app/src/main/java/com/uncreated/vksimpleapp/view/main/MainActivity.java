package com.uncreated.vksimpleapp.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.uncreated.vksimpleapp.presenter.MainPresenter;
import com.uncreated.vksimpleapp.view.AutoGridLayoutManager;
import com.uncreated.vksimpleapp.view.auth.AuthActivity;
import com.uncreated.vksimpleapp.view.photo.PhotoActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int AUTH_CODE = 1;
    private static final int PHOTO_CODE = 2;

    @Inject
    App app;

    @Named("keyPhotoIndex")
    @Inject
    String keyPhotoIndex;

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.rv_photos)
    RecyclerView recyclerViewPhotos;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    public MainActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    private PhotosAdapter photosAdapter;

    private NavigationViewHolder navigationViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        navigationViewHolder = new NavigationViewHolder(navigationView.getHeaderView(0));
        navigationView.setNavigationItemSelectedListener(getNavigationListener());

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        photosAdapter = new PhotosAdapter(0);
        app.getAppComponent().inject(photosAdapter);

        ((SimpleItemAnimator) recyclerViewPhotos.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerViewPhotos.setLayoutManager(new AutoGridLayoutManager(this, 100));
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        MainPresenter mainPresenter = new MainPresenter();
        app.getAppComponent().inject(mainPresenter);
        return mainPresenter;
    }

    @Override
    public void goAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivityForResult(intent, AUTH_CODE);
    }

    @Override
    public void goPhoto(int index) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra(keyPhotoIndex, index);
        startActivityForResult(intent, PHOTO_CODE);
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
    public void setGallery(int size) {
        toolbar.setTitle("Фотографий: " + size);

        photosAdapter.setPhotosCount(size);
        photosAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateThumbnail(int index) {
        photosAdapter.notifyItemChanged(index);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener getNavigationListener() {
        return item -> {
            int id = item.getItemId();

            if (id == R.id.nav_camera) {

            } else if (id == R.id.nav_gallery) {

            } else if (id == R.id.nav_slideshow) {

            } else if (id == R.id.nav_manage) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        };
    }
}
