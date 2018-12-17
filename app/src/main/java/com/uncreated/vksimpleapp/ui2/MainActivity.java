package com.uncreated.vksimpleapp.ui2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.ui2.auth.view.AuthFragment;

public class MainActivity extends AppCompatActivity implements MainNavigationCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        if (savedInstanceState == null) {
            goAuth();
        }
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void goAuth() {
        switchFragment(new AuthFragment());
    }

    @Override
    public void goGallery() {
        //switchFragment();//TODO: Gallery
    }
}
