package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    public Repository repository(IAuthRepository authRepository,
                                 UserRepository userRepository,
                                 GalleryRepository galleryRepository,
                                 PhotoRepository photoRepository) {
        return new Repository(authRepository, userRepository, galleryRepository, photoRepository);
    }

    @Singleton
    @Provides
    public UserRepository userRepository(ApiService apiService, EventBus eventBus) {
        return new UserRepository(apiService, eventBus);
    }

    @Singleton
    @Provides
    public GalleryRepository galleryRepository(ApiService apiService, EventBus eventBus) {
        return new GalleryRepository(apiService, eventBus);
    }

}
