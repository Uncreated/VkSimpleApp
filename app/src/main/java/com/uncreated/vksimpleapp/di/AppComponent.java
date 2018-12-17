package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.di.modules.ApiModule;
import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.AuthModule;
import com.uncreated.vksimpleapp.di.modules.PhotoRepositoryModule;
import com.uncreated.vksimpleapp.di.modules.RepositoryModule;
import com.uncreated.vksimpleapp.ui.main.MainViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryFragment;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.activity.PhotoViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.page.PageViewModel;
import com.uncreated.vksimpleapp.ui.main.fragments.settings.SettingsViewModel;
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthViewModel;
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthWebViewClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        AuthModule.class,
        ApiModule.class,
        RepositoryModule.class,
        PhotoRepositoryModule.class})
public interface AppComponent {
    void inject(MainViewModel mainViewModel);

    void inject(PhotoViewModel photoViewModel);

    void inject(SettingsViewModel settingsViewModel);

    void inject(AuthViewModel authViewModel);

    void inject(PageViewModel pageViewModel);

    void inject(GalleryFragment galleryFragment);

    void inject(GalleryViewModel galleryViewModel);

    void inject(com.uncreated.vksimpleapp.ui2.fragments.main.viewmodel.MainViewModel mainViewModel);

    void inject(com.uncreated.vksimpleapp.ui2.modelview.MainViewModel mainViewModel);

    void inject(AuthWebViewClient authWebViewClient);
}
