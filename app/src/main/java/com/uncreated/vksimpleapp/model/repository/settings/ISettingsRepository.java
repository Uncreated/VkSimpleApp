package com.uncreated.vksimpleapp.model.repository.settings;

public interface ISettingsRepository {

    int getDefaultThemeId();

    boolean getThemeValue();

    void setThemeValue(boolean value);
}
