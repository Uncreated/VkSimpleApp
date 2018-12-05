package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.eventbus.BitmapEvents;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Module
public class EventBusModule {

    @Singleton
    @Provides
    public EventBus eventBus(BitmapEvents thumbnailEvents,
                             BitmapEvents originalEvents) {
        return new EventBus(thumbnailEvents,
                originalEvents,
                publishSubject(),
                behaviorSubject(),
                behaviorSubject(),
                publishSubject());
    }

    @Provides
    public BitmapEvents bitmapEvents() {
        return new BitmapEvents(publishSubject(), publishSubject(), publishSubject());
    }

    private <T> PublishSubject<T> publishSubject() {
        return PublishSubject.create();
    }

    private <T> BehaviorSubject<T> behaviorSubject() {
        return BehaviorSubject.create();
    }
}
