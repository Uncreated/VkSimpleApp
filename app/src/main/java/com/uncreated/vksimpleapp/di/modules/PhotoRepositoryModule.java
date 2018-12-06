package com.uncreated.vksimpleapp.di.modules;

import android.content.Context;

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

    @Named("totalImageCount")
    @Provides
    public Integer totalImageCount() {
        return 40;
    }

    @Singleton
    @Provides
    public GalleryPhotoCache originalsCache(@Named("totalImageCount") Integer totalImageCount) {
        return new GalleryPhotoCache(totalImageCount);
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
    public PhotoRepository photoRepository(@Named("cache") IPhotoLoader photoLoader,
                                           GalleryPhotoCache thumbnailsCache) {
        return new PhotoRepository(photoLoader, thumbnailsCache);
    }


}
