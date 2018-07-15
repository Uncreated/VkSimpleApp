package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.EventBusModule;
import com.uncreated.vksimpleapp.presenter.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        EventBusModule.class})
public interface TestMainPresenterComponent {
    void inject(MainPresenter mainPresenter);
}
