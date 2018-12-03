package com.uncreated.vksimpleapp.view.auth;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.ActivityAuthBinding;
import com.uncreated.vksimpleapp.presenter.AuthViewModel;
import com.uncreated.vksimpleapp.view.main.MainActivity;

public class AuthActivity extends MvpAppCompatActivity {

    private ActivityAuthBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

        AuthViewModel authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        authViewModel.getAuthSuccessfulLiveData()
                .observe(this, successful -> {
                    if (successful != null && successful) {
                        goMain();
                    }
                });

        authViewModel.getAuthWebClientLiveData()
                .observe(this, authWebClient -> {
                    if (authWebClient != null) {
                        goUrl(authWebClient.getAuthUrl(), authWebClient);
                    }
                });
    }

    private void goUrl(String url, WebViewClient webViewClient) {
        dataBinding.wvOauth.clearCache(true);
        dataBinding.wvOauth.clearHistory();

        dataBinding.wvOauth.setWebViewClient(webViewClient);
        dataBinding.wvOauth.loadUrl(url);
    }

    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
