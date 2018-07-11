package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.api.VkErrorHandler;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.Repositories;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryStorageRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserStorageRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    public Repositories repository(IAuthRepository authRepository,
                                   Repository<User> userRepository,
                                   Repository<Gallery> galleryRepository,
                                   PhotoRepository photoRepository,
                                   VkErrorHandler vkErrorHandler) {
        return new Repositories(authRepository,
                userRepository,
                galleryRepository,
                photoRepository,
                vkErrorHandler);
    }

    @Singleton
    @Provides
    public Repository<User> userRepository(ApiService apiService, EventBus eventBus) {
        return new UserStorageRepository(apiService, eventBus);
    }

    @Singleton
    @Provides
    public Repository<Gallery> galleryRepository(ApiService apiService, EventBus eventBus) {
        return new GalleryStorageRepository(apiService, eventBus);
    }

}
