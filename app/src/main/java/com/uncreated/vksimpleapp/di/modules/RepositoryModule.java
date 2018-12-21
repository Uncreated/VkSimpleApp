package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.repository.auth.AuthRepository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public GalleryRepository galleryRepository(@Named("web") GalleryLoader galleryWebLoader,
                                               @Named("storage") GalleryLoader galleryStorageLoader) {
        return new GalleryRepository(galleryWebLoader, galleryStorageLoader);
    }

    @Singleton
    @Provides
    public IAuthRepository authRepository(@Named("Auth") SharedPreferences sharedPreferences) {
        return new AuthRepository(sharedPreferences);
    }

    /*@Singleton
    @Provides
    public UserRepository userRepository(UserWebLoader userWebLoader,
                                         UserStorageLoader userStorageLoader) {
        return new UserRepository(userWebLoader, userStorageLoader);
    }

    @Singleton
    @Provides
    public GalleryRepository galleryRepository(GalleryWebLoader galleryWebLoader,
                                               GalleryStorageLoader galleryStorageLoader) {
        return new GalleryRepository(galleryWebLoader, galleryStorageLoader);
    }

    @Singleton
    @Provides
    public GalleryWebLoader galleryWebLoader(ApiService apiService) {
        return new GalleryWebLoader(apiService);
    }

    @Singleton
    @Provides
    public GalleryStorageLoader galleryStorageLoader() {
        return new GalleryStorageLoader();
    }

    @Singleton
    @Provides
    public UserWebLoader userWebLoader(ApiService apiService) {
        return new UserWebLoader(apiService);
    }

    @Singleton
    @Provides
    public UserStorageLoader userStorageLoader() {
        return new UserStorageLoader();
    }

    @Singleton
    @Provides
    public ISettingsRepository settingsRepository(@Named("Settings") SharedPreferences sharedPreferences) {
        return new SettingsRepository(sharedPreferences);
    }*/
}
