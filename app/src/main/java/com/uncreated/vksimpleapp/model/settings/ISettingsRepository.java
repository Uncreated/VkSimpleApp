package com.uncreated.vksimpleapp.model.settings;

public interface ISettingsRepository {

    int getDefaultThemeId();

    boolean getThemeValue();

    void setThemeValue(boolean value);
}
