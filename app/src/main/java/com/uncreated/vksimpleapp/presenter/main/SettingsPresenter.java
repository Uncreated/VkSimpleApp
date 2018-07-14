package com.uncreated.vksimpleapp.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.view.main.settings.SettingsView;

import javax.inject.Inject;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    @Inject
    ISettingsRepository settingsRepository;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setThemeValue(settingsRepository.getThemeValue());
    }

    public void setThemeValue(boolean value) {
        settingsRepository.setThemeValue(value);
    }
}
