package com.uncreated.vksimpleapp.model.settings;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.R;

public class SettingsRepository implements ISettingsRepository {
    private static final String THEME_KEY = "keyTheme";

    private SharedPreferences sharedPreferences;

    public SettingsRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public int getDefaultThemeId() {
        if (getThemeValue()) {
            return R.style.AppTheme_Dark;
        } else {
            return R.style.AppTheme_Light;
        }
    }

    @Override
    public boolean getThemeValue() {
        return sharedPreferences.getBoolean(THEME_KEY, false);
    }

    @Override
    public void setThemeValue(boolean value) {
        sharedPreferences.edit()
                .putBoolean(THEME_KEY, value)
                .apply();
    }
}
