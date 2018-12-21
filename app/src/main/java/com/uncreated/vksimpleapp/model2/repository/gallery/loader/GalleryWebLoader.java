package com.uncreated.vksimpleapp.model2.repository.gallery.loader;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryParameters;

import java.io.IOException;

public class GalleryWebLoader extends GalleryLoader {
    private static final int COUNT_PER_REQUEST = 200;

    private ApiService apiService;
    private WebLoader<Gallery> galleryWebLoader;

    public GalleryWebLoader(ApiService apiService, WebLoader<Gallery> galleryWebLoader) {
        this.apiService = apiService;
        this.galleryWebLoader = galleryWebLoader;
    }

    @Override
    public Gallery load(GalleryParameters parameters) throws IOException {
        Gallery gallery = galleryWebLoader.load(apiService.getCallGallery(parameters.getUserId(),
                true, parameters.getOffset(), COUNT_PER_REQUEST));
        gallery.sort();

        return gallery;
    }
}
