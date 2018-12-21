package com.uncreated.vksimpleapp.ui2.fragments.main.view;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.FragmentMainBinding;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.ui.main.NavigationViewHolder;
import com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.view.GalleryFragment;
import com.uncreated.vksimpleapp.ui2.fragments.main.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;

    private FragmentMainBinding binding;

    private NavigationViewHolder navigationViewHolder;
    private NavigationView.OnNavigationItemSelectedListener selectedListener;

    private static int curFragment = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,
                container, false);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initActivityViews();

        if (savedInstanceState == null) {
            initFragment();
        }

        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releaseActivityViews();
    }

    private void initFragment() {
        selectedListener.onNavigationItemSelected(binding.navView.getMenu().getItem(0));
    }

    private void initNavigation() {
        navigationViewHolder = new NavigationViewHolder(binding.navView.getHeaderView(0));
        selectedListener = getNavigationListener();
        binding.navView.setNavigationItemSelectedListener(selectedListener);
        initToggle();
    }

    private void initToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), binding.drawerLayout,
                binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initActivityViews() {
        if (getActivity() == null) {
            throw new RuntimeException("Activity needed");
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);

        initNavigation();
    }

    private void releaseActivityViews() {
        if (getActivity() == null) {
            throw new RuntimeException("Activity needed");
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(null);
    }

    private NavigationView.OnNavigationItemSelectedListener getNavigationListener() {
        return item -> {
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.nav_gallery:
                    switchFragment(new GalleryFragment(), R.layout.fragment_gallery2);
                    break;
                case R.id.nav_settings:
                    //switchFragment(new SettingsFragment());
                    break;
                case R.id.nav_logout:
                    item.setChecked(false);
                    viewModel.onLogout();
                    break;
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        };
    }

    private void switchFragment(Fragment fragment, int id) {
        if (curFragment != id) {
            curFragment = id;
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
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
}
