package com.uncreated.vksimpleapp;

import android.app.Application;

import com.uncreated.vksimpleapp.di.AppComponent;
import com.uncreated.vksimpleapp.di.DaggerAppComponent;
import com.uncreated.vksimpleapp.di.modules.AppModule;

import timber.log.Timber;

public class App extends Application {

    private static App app;

    private AppComponent appComponent;

    public static App getApp() {
        return app;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        Timber.plant(new Timber.DebugTree());
    }


}
