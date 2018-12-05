package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.Repositories;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryStorageLoader;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryWebLoader;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserStorageLoader;
import com.uncreated.vksimpleapp.model.repository.user.UserWebLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    public Repositories repository(IAuthRepository authRepository,
                                   PhotoRepository photoRepository,
                                   GalleryRepository galleryRepository,
                                   UserRepository userRepository,
                                   ISettingsRepository settingsRepository) {
        return new Repositories(authRepository,
                photoRepository,
                galleryRepository,
                userRepository,
                settingsRepository);
    }

    @Singleton
    @Provides
    public UserRepository userRepository(UserWebLoader userWebLoader,
                                         UserStorageLoader userStorageLoader) {
        return new UserRepository(userWebLoader, userStorageLoader);
    }

    @Singleton
    @Provides
    public GalleryRepository galleryRepository(EventBus eventBus,
                                               GalleryWebLoader galleryWebLoader,
                                               GalleryStorageLoader galleryStorageLoader) {
        return new GalleryRepository(eventBus, galleryWebLoader, galleryStorageLoader);
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
}
