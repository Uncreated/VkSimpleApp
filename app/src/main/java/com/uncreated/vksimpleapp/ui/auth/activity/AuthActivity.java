package com.uncreated.vksimpleapp.ui.auth.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.ui.main.MainActivity;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        AuthViewModel authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        authViewModel.getAuthSuccessfulLiveData()
                .observe(this, successful -> {
                    if (successful != null && successful) {
                        goMain();
                    }
                });
    }

    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
