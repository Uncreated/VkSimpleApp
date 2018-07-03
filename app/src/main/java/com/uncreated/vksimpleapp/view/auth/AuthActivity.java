package com.uncreated.vksimpleapp.view.auth;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.presenter.AuthPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends MvpAppCompatActivity implements AuthView {

    @Inject
    App app;

    @InjectPresenter
    AuthPresenter authPresenter;

    @BindView(R.id.wv_oauth)
    WebView webView;

    public AuthActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public AuthPresenter provideMainPresenter() {
        AuthPresenter authPresenter = new AuthPresenter();
        app.getAppComponent().inject(authPresenter);
        return authPresenter;
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "showLoading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(this, "hideLoading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void go(String url, WebViewClient webViewClient) {
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goBackView() {
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
