package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.di.modules.ApiModule;
import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.AuthModule;
import com.uncreated.vksimpleapp.di.modules.EventBusModule;
import com.uncreated.vksimpleapp.di.modules.PhotoRepositoryModule;
import com.uncreated.vksimpleapp.di.modules.RepositoryModule;
import com.uncreated.vksimpleapp.di.modules.SettingsModule;
import com.uncreated.vksimpleapp.presenter.AuthPresenter;
import com.uncreated.vksimpleapp.presenter.PhotoPresenter;
import com.uncreated.vksimpleapp.presenter.main.GalleryPresenter;
import com.uncreated.vksimpleapp.presenter.main.MainPresenter;
import com.uncreated.vksimpleapp.presenter.main.SettingsPresenter;
import com.uncreated.vksimpleapp.view.auth.AuthActivity;
import com.uncreated.vksimpleapp.view.main.MainActivity;
import com.uncreated.vksimpleapp.view.main.gallery.GalleryFragment;
import com.uncreated.vksimpleapp.view.main.gallery.PhotosAdapter;
import com.uncreated.vksimpleapp.view.main.settings.SettingsFragment;
import com.uncreated.vksimpleapp.view.photo.PageFragment;
import com.uncreated.vksimpleapp.view.photo.PhotoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        AuthModule.class,
        ApiModule.class,
        RepositoryModule.class,
        EventBusModule.class,
        PhotoRepositoryModule.class,
        SettingsModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(AuthActivity authActivity);

    void inject(AuthPresenter authPresenter);

    void inject(PhotoActivity photoActivity);

    void inject(PhotoPresenter photoPresenter);

    void inject(PhotosAdapter photosAdapter);

    void inject(PageFragment pageFragment);

    void inject(GalleryFragment galleryFragment);

    void inject(GalleryPresenter galleryPresenter);

    void inject(SettingsFragment settingsFragment);

    void inject(SettingsPresenter settingsPresenter);
}
