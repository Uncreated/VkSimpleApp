package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.settings.SettingsRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {
    @Singleton
    @Provides
    public ISettingsRepository settingsRepository(@Named("Settings") SharedPreferences sharedPreferences) {
        return new SettingsRepository(sharedPreferences);
    }
}
