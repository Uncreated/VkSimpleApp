package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.settings.SettingsRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {
    @Provides
    public ISettingsRepository settingsRepository(EventBus eventBus,
                                                  @Named("Settings") SharedPreferences sharedPreferences) {
        return new SettingsRepository(eventBus, sharedPreferences);
    }

    @Named("themeId")
    @Provides
    public Integer themeId(ISettingsRepository settingsRepository) {
        return settingsRepository.getDefaultThemeId();
    }
}
