package com.uncreated.vksimpleapp.model.repository.settings.entities;

import android.content.SharedPreferences;

public class SettingsValues {
    private static final String KEY_THEME_VALUE = "_keyThemeValue";

    private SettingsParamBoolean themeBlack = new SettingsParamBoolean(KEY_THEME_VALUE);

    public SettingsValues(SharedPreferences sharedPreferences, String keyPrefix) {
        themeBlack.init(sharedPreferences, keyPrefix);
    }

    public boolean save(SharedPreferences sharedPreferences, String keyPrefix) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        themeBlack.save(editor, keyPrefix);

        return editor.commit();
    }

    public SettingsParamBoolean getThemeParam() {
        return themeBlack;
    }

    public void setThemeBlack(SettingsParamBoolean themeBlack) {
        this.themeBlack = themeBlack;
    }
}
