package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.UserWebLoaderInstrumentedTest;
import com.uncreated.vksimpleapp.di.modules.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class,
        EventBusModule.class})
public interface InstrumentedTestComponent {

    void inject(UserWebLoaderInstrumentedTest userWebLoaderInstrumentedTest);
}
