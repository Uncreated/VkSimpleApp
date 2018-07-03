package com.uncreated.vksimpleapp.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.App;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

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
    public SharedPreferences sharedPreferences(App app) {
        return app.getApplicationContext().getSharedPreferences("Auth", Context.MODE_PRIVATE);
    }
}
