package com.uncreated.vksimpleapp.ui2.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.ui2.fragments.auth.view.AuthFragment;
import com.uncreated.vksimpleapp.ui2.fragments.main.view.MainFragment;
import com.uncreated.vksimpleapp.ui2.modelview.MainViewModel;

public class MainActivity extends AppCompatActivity implements MainNavigationCallback {

    private static int curFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getIsAuthValidLiveData()
                .observe(this, isValid -> {
                    if (isValid) {
                        goMain();
                    } else {
                        goAuth();
                    }
                });
    }

    private void switchFragment(Fragment fragment, int id) {
        if (curFragment != id) {
            curFragment = id;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void goAuth() {
        switchFragment(new AuthFragment(), R.layout.fragment_auth);
    }

    @Override
    public void goMain() {
        switchFragment(new MainFragment(), R.layout.fragment_main);
    }
}
