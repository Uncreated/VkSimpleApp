package com.uncreated.vksimpleapp.model.repository.settings.entities;

import android.content.SharedPreferences;

public class SettingsParamBoolean extends SettingsParam<Boolean> {

    public SettingsParamBoolean(String key) {
        super(key);
    }

    @Override
    public void init(SharedPreferences sharedPreferences, String prefix) {
        setValue(sharedPreferences.getBoolean(getKey(prefix), false));
    }

    @Override
    public void save(SharedPreferences.Editor editor, String prefix) {
        editor.putBoolean(getKey(prefix), getValue());
    }
}
