package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {

    @Singleton
    @Provides
    public EventBus eventBus() {
        return new EventBus();
    }
}
