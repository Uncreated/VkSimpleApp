package com.uncreated.vksimpleapp.di.modules;

import android.content.Context;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.gallery.IGalleryRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.WebGalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.IPhotoRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryCache;
import com.uncreated.vksimpleapp.model.repository.user.IUserRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepositoryWeb;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Named("web")
    @Singleton
    @Provides
    public Repository webRepository(@Named("web") IUserRepository userRepository,
                                    @Named("web") IGalleryRepository galleryRepository,
                                    @Named("web") IPhotoRepository photoRepository) {
        return new Repository(userRepository, galleryRepository, photoRepository);
    }

    @Named("web")
    @Singleton
    @Provides
    public IUserRepository webUserRepository(ApiService apiService, EventBus eventBus) {
        return new UserRepositoryWeb(apiService, eventBus);
    }

    @Named("web")
    @Singleton
    @Provides
    public IGalleryRepository webGalleryRepository(ApiService apiService, EventBus eventBus) {
        return new WebGalleryRepository(apiService, eventBus);
    }

    @Named("web")
    @Singleton
    @Provides
    public IPhotoRepository webPhotoRepository(@Named("application") Context context,
                                               @Named("thumbnail") GalleryCache thumbnailsCache,
                                               @Named("original") GalleryCache originalsCache,
                                               EventBus eventBus) {
        return new PhotoRepository(context, eventBus, thumbnailsCache, originalsCache);
    }

    @Named("thumbnail")
    @Singleton
    @Provides
    public GalleryCache thumbnailsCache() {
        return new GalleryCache(40);
    }

    @Named("original")
    @Singleton
    @Provides
    public GalleryCache originalsCache() {
        return new GalleryCache(3);
    }

}
