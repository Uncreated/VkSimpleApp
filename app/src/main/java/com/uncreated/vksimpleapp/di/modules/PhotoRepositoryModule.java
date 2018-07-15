package com.uncreated.vksimpleapp.di.modules;

import android.content.Context;

import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.photo.loader.IPhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.loader.StoragePhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.loader.WebPhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoRepositoryModule {

    @Named("thumbnailsCount")
    @Provides
    public Integer thumbnailsCacheSize() {
        return 40;
    }

    @Named("originalsCount")
    @Provides
    public Integer originalsCacheSize() {
        return 3;
    }

    @Named("thumbnail")
    @Singleton
    @Provides
    public GalleryPhotoCache thumbnailsCache(@Named("thumbnailsCount") Integer thumbnailsCount) {
        return new GalleryPhotoCache(thumbnailsCount);
    }

    @Named("original")
    @Singleton
    @Provides
    public GalleryPhotoCache originalsCache(@Named("originalsCount") Integer originalsCount) {
        return new GalleryPhotoCache(originalsCount);
    }

    @Named("web")
    @Singleton
    @Provides
    public IPhotoLoader webPhotoLoader(@Named("application") Context context) {
        return new WebPhotoLoader(context);
    }

    @Named("cache")
    @Singleton
    @Provides
    public IPhotoLoader storagePhotoLoader(@Named("application") Context context) {
        return new StoragePhotoLoader(context);
    }

    @Singleton
    @Provides
    public PhotoRepository photoRepository(EventBus eventBus,
                                           @Named("cache") IPhotoLoader webPhotoLoader,
                                           @Named("thumbnail") GalleryPhotoCache thumbnailsCache,
                                           @Named("original") GalleryPhotoCache originalsCache) {
        return new PhotoRepository(eventBus, webPhotoLoader, thumbnailsCache, originalsCache);
    }


}
