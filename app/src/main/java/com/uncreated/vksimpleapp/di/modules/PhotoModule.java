package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model2.repository.photo.PhotoLoader;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model2.repository.photo.loader.PhotoRamLoader;
import com.uncreated.vksimpleapp.model2.repository.photo.loader.PhotoStorageLoader;
import com.uncreated.vksimpleapp.model2.repository.photo.loader.PhotoWebLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoModule {

    @Singleton
    @Provides
    public PhotoRepository photoRepository(@Named("web") PhotoLoader photoWebLoader,
                                           @Named("storage") PhotoLoader photoStorageLoader,
                                           @Named("ram") PhotoLoader photoRamLoader) {
        return new PhotoRepository(photoWebLoader, photoStorageLoader, photoRamLoader);
    }

    @Named("web")
    @Singleton
    @Provides
    public PhotoLoader photoWebLoader() {
        return new PhotoWebLoader();
    }

    @Named("storage")
    @Singleton
    @Provides
    public PhotoLoader photoStorageLoader() {
        return new PhotoStorageLoader();
    }

    @Named("ram")
    @Singleton
    @Provides
    public PhotoLoader photoRamLoader() {
        return new PhotoRamLoader(30);
    }
}
