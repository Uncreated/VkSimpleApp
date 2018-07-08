package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {

    @Singleton
    @Provides
    public EventBus eventBus(@Named("thumbnailsCount") Integer thumbnailsCount,
                             @Named("originalsCount") Integer originalsCount) {
        return new EventBus(thumbnailsCount, originalsCount);
    }
}
