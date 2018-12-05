package com.uncreated.vksimpleapp.model.repository.settings;

import com.uncreated.vksimpleapp.model.repository.settings.entities.SettingsValues;

import io.reactivex.Observable;

public interface ISettingsRepository {

    int getDefaultThemeId();

    Observable<SettingsValues> getSettingsValues();

    void setSettingsValues(SettingsValues settingsValues);
}
