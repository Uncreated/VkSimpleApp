package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.di.modules.ApiModule;
import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.AuthModule;
import com.uncreated.vksimpleapp.di.modules.EventBusModule;
import com.uncreated.vksimpleapp.di.modules.RepositoryModule;
import com.uncreated.vksimpleapp.presenter.AuthPresenter;
import com.uncreated.vksimpleapp.presenter.MainPresenter;
import com.uncreated.vksimpleapp.presenter.PhotoPresenter;
import com.uncreated.vksimpleapp.view.auth.AuthActivity;
import com.uncreated.vksimpleapp.view.main.MainActivity;
import com.uncreated.vksimpleapp.view.main.PhotosAdapter;
import com.uncreated.vksimpleapp.view.photo.PageFragment;
import com.uncreated.vksimpleapp.view.photo.PhotoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        AuthModule.class,
        ApiModule.class,
        RepositoryModule.class,
        EventBusModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(AuthActivity authActivity);

    void inject(AuthPresenter authPresenter);

    void inject(PhotoActivity photoActivity);

    void inject(PhotoPresenter photoPresenter);

    void inject(PhotosAdapter photosAdapter);

    void inject(PageFragment pageFragment);
}
