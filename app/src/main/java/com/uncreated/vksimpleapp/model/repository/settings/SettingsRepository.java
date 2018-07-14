package com.uncreated.vksimpleapp.model.repository.settings;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.EventBus;

public class SettingsRepository implements ISettingsRepository {
    private static final String THEME_KEY = "keyTheme";

    private EventBus eventBus;
    private SharedPreferences sharedPreferences;

    private boolean themeValue;

    public SettingsRepository(EventBus eventBus, SharedPreferences sharedPreferences) {
        this.eventBus = eventBus;
        this.sharedPreferences = sharedPreferences;

        this.themeValue = sharedPreferences.getBoolean(THEME_KEY, false);
    }

    @Override
    public int getDefaultThemeId() {
        if (themeValue) {
            return R.style.AppTheme_Dark;
        } else {
            return R.style.AppTheme_Light;
        }
    }

    @Override
    public boolean getThemeValue() {
        return themeValue;
    }

    @Override
    public void setThemeValue(boolean value) {
        if (themeValue != value) {
            themeValue = value;
            sharedPreferences.edit()
                    .putBoolean(THEME_KEY, themeValue)
                    .apply();

            eventBus.getThemeIdSubject()
                    .onNext(getDefaultThemeId());
        }
    }
}
