package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model2.api.ApiService;
import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model2.repository.gallery.loader.GalleryStorageLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.loader.GalleryWebLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryModule {

    @Singleton
    @Provides
    public GalleryRepository galleryRepository(@Named("web") GalleryLoader galleryWebLoader,
                                               @Named("storage") GalleryLoader galleryStorageLoader) {
        return new GalleryRepository(galleryWebLoader, galleryStorageLoader);
    }

    @Named("web")
    @Singleton
    @Provides
    public GalleryLoader galleryWebLoader(ApiService apiService, WebLoader webLoader) {
        return new GalleryWebLoader(apiService, webLoader);
    }

    @Named("storage")
    @Singleton
    @Provides
    public GalleryLoader galleryStorageLoader() {
        return new GalleryStorageLoader();
    }
}
