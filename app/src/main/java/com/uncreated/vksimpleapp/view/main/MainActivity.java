package com.uncreated.vksimpleapp.view.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.presenter.MainPresenter;
import com.uncreated.vksimpleapp.view.auth.AuthActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int AUTH_CODE = 1;

    @Inject
    App app;

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.tv_name)
    TextView textViewName;

    @BindView(R.id.iv_avatar)
    ImageView imageViewAvatar;

    private AlertDialog dialog;

    public MainActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTH_CODE) {
            mainPresenter.onAuthResult();
        }
    }

    @Override
    public void setUserName(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        textViewName.setText(fullName);
    }

    @Override
    public void setUserAvatar() {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Loading...")
                    .create();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }
}
