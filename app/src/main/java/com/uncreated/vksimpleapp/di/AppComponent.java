package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.di.modules.ApiModule;
import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.AuthModule;
import com.uncreated.vksimpleapp.di.modules.PhotoRepositoryModule;
import com.uncreated.vksimpleapp.di.modules.RepositoryModule;
import com.uncreated.vksimpleapp.di.modules.SettingsModule;
import com.uncreated.vksimpleapp.ui.auth.activity.AuthActivity;
import com.uncreated.vksimpleapp.ui.auth.activity.AuthViewModel;
import com.uncreated.vksimpleapp.ui.auth.web.AuthWebViewModel;
import com.uncreated.vksimpleapp.ui.main.MainActivity;
import com.uncreated.vksimpleapp.ui.main.MainViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryAdapter;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryFragment;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.activity.PhotoActivity;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.activity.PhotoViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.page.PageFragment;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.page.PageViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.settings.SettingsFragment;
import com.uncreated.vksimpleapp.ui.main.fragments.settings.SettingsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        AuthModule.class,
        ApiModule.class,
        RepositoryModule.class,
        PhotoRepositoryModule.class,
        SettingsModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity mainActivity);

    void inject(MainViewModel mainViewModel);

    void inject(AuthActivity authActivity);

    void inject(AuthViewModel authViewModel);

    void inject(PhotoActivity photoActivity);

    void inject(PhotoViewModel photoViewModel);

    void inject(GalleryAdapter galleryAdapter);

    void inject(PageFragment pageFragment);

    void inject(GalleryFragment galleryFragment);

    void inject(GalleryViewModel galleryViewModel);

    void inject(SettingsFragment settingsFragment);

    void inject(SettingsViewModel settingsViewModel);

    void inject(AuthWebViewModel authWebViewModel);

    void inject(PageViewModel pageViewModel);
}
