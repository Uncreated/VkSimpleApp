package com.uncreated.vksimpleapp.di;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusTestModule {
    @Provides
    public EventBus eventBus() {
        return null;
    }
}
