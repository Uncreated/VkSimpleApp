package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.loader.GalleryStorageLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.loader.GalleryWebLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoaderModule {

    @Named("web")
    @Singleton
    @Provides
    public GalleryLoader galleryWebLoader(ApiService apiService,
                                          @Named("web") WebLoader<Gallery> galleryWebLoader) {
        return new GalleryWebLoader(apiService, galleryWebLoader);
    }

    @Named("storage")
    @Singleton
    @Provides
    public GalleryLoader galleryStorageLoader() {
        return new GalleryStorageLoader();
    }

    @Named("gallery")
    @Provides
    public WebLoader<Gallery> webLoaderGallery() {
        return new WebLoader<>();
    }
}
