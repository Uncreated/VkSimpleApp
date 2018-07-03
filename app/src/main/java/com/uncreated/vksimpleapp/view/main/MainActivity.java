package com.uncreated.vksimpleapp.view.main;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.presenter.MainPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @Inject
    App app;

    @InjectPresenter
    MainPresenter mainPresenter;

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
}
