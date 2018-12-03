package com.uncreated.vksimpleapp.ui.main.fragments.settings;

import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;

import javax.inject.Inject;

public class SettingsViewModel extends ViewModel {

    @Inject
    ISettingsRepository settingsRepository;

    public SettingsViewModel() {
        App.getApp().getAppComponent().inject(this);
    }

    public void setThemeValue(boolean value) {
        settingsRepository.setThemeValue(value);
    }

    public boolean getThemeValue() {
        return settingsRepository.getThemeValue();
    }
}
