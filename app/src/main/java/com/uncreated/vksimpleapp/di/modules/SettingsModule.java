package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.settings.SettingsRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {
    @Provides
    public ISettingsRepository settingsRepository(@Named("Settings") SharedPreferences sharedPreferences) {
        return new SettingsRepository(sharedPreferences);
    }

    @Named("themeId")
    @Provides
    public Integer themeId(ISettingsRepository settingsRepository) {
        return settingsRepository.getDefaultThemeId();
    }
}
