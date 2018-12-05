package com.uncreated.vksimpleapp.model.repository.settings.entities;

import android.content.SharedPreferences;

public abstract class SettingsParam<T> {
    private String key;

    private T value;

    public SettingsParam(String key) {
        this.key = key;
    }

    public abstract void init(SharedPreferences sharedPreferences, String prefix);

    public abstract void save(SharedPreferences.Editor editor, String prefix);

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    protected String getKey(String prefix) {
        return prefix + key;
    }
}
