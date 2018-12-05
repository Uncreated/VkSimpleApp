package com.uncreated.vksimpleapp.model.repository.settings;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.repository.settings.entities.SettingsValues;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class SettingsRepository implements ISettingsRepository {
    private static final String SETTINGS_VALUES = "settingsValues";

    private SharedPreferences sharedPreferences;

    private SettingsValues settingsValues;

    private Subject<SettingsValues> valuesSubject = BehaviorSubject.create();

    public SettingsRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        settingsValues = new SettingsValues(sharedPreferences, SETTINGS_VALUES);
    }

    @Override
    public Observable<SettingsValues> getSettingsValues() {
        return valuesSubject;
    }

    @Override
    public void setSettingsValues(SettingsValues settingsValues) {
        settingsValues.save(sharedPreferences, SETTINGS_VALUES);
        valuesSubject.onNext(settingsValues);
    }

    @Override
    public int getDefaultThemeId() {
        if (settingsValues.getThemeParam().getValue()) {
            return R.style.AppTheme_Dark;
        } else {
            return R.style.AppTheme_Light;
        }
    }
}
