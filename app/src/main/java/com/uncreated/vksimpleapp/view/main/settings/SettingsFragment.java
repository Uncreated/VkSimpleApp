package com.uncreated.vksimpleapp.view.main.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.presenter.main.SettingsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends MvpAppCompatFragment implements SettingsView {

    @Inject
    App app;

    @BindView(R.id.tv_theme_label)
    TextView textViewThemeLabel;

    @BindView(R.id.tv_theme_left_hint)
    TextView textViewLeftHint;

    @BindView(R.id.tv_theme_right_hint)
    TextView textViewRightHint;

    @BindView(R.id.sw_theme)
    Switch switchTheme;

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    public SettingsFragment() {
        App.getApp().getAppComponent().inject(this);
    }

    @ProvidePresenter
    public SettingsPresenter provideSettingsPresenter() {
        SettingsPresenter settingsPresenter = new SettingsPresenter();
        app.getAppComponent().inject(settingsPresenter);
        return settingsPresenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);

        switchTheme.setOnCheckedChangeListener(getCheckedChangeListener());

        return view;
    }

    private CompoundButton.OnCheckedChangeListener getCheckedChangeListener() {
        return (buttonView, isChecked) -> {
            textViewLeftHint.setEnabled(!isChecked);
            textViewRightHint.setEnabled(isChecked);

            settingsPresenter.setThemeValue(isChecked);
        };
    }

    @Override
    public void setThemeValue(boolean value) {
        switchTheme.setChecked(value);
    }
}
