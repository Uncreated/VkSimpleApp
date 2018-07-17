package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusTestModule {
    @Provides
    public EventBus eventBus() {
        return null;
    }
}
