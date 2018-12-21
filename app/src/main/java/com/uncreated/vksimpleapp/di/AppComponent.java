package com.uncreated.vksimpleapp.di;

import com.uncreated.vksimpleapp.di.modules.ApiModule;
import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.AuthModule;
import com.uncreated.vksimpleapp.di.modules.LoaderModule;
import com.uncreated.vksimpleapp.di.modules.RepositoryModule;
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthViewModel;
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthWebViewClient;
import com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.view.GalleryFragment;
import com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.viewmodel.GalleryViewModel;
import com.uncreated.vksimpleapp.ui2.modelview.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        AuthModule.class,
        ApiModule.class,
        RepositoryModule.class,
        LoaderModule.class})
public interface AppComponent {
    //void inject(MainViewModel mainViewModel);

    //void inject(PhotoViewModel photoViewModel);

    //void inject(SettingsViewModel settingsViewModel);

    void inject(AuthViewModel authViewModel);

    //void inject(PageViewModel pageViewModel);

    //void inject(GalleryFragment galleryFragment);

    //void inject(GalleryViewModel galleryViewModel);

    void inject(com.uncreated.vksimpleapp.ui2.fragments.main.viewmodel.MainViewModel mainViewModel);

    void inject(MainViewModel mainViewModel);

    void inject(AuthWebViewClient authWebViewClient);

    void inject(GalleryFragment galleryFragment);

    void inject(GalleryViewModel galleryViewModel);
}
