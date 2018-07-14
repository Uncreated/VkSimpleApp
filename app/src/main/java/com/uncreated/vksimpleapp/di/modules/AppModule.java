package com.uncreated.vksimpleapp.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.App;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class AppModule {
    private App app;

    public AppModule(com.uncreated.vksimpleapp.App app) {
        this.app = app;
    }

    @Provides
    public App app() {
        return app;
    }

    @Named("Auth")
    @Provides
    public SharedPreferences sharedPreferencesAuth(App app) {
        return app.getSharedPreferences("Auth", Context.MODE_PRIVATE);
    }

    @Named("Settings")
    @Provides
    public SharedPreferences sharedPreferencesSettings(App app) {
        return app.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    @Named("mainThread")
    @Provides
    public Scheduler mainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Named("application")
    @Provides
    public Context applicationContext() {
        return app.getApplicationContext();
    }

    @Named("keyPhotoIndex")
    @Provides
    public String getPhotoUrl() {
        return "keyPhotoIndex";
    }
}
