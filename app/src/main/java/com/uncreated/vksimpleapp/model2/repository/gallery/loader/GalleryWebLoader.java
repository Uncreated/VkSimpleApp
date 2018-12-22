package com.uncreated.vksimpleapp.model2.repository.gallery.loader;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.api.ApiService;
import com.uncreated.vksimpleapp.model2.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryParameters;

import java.io.IOException;

public class GalleryWebLoader extends GalleryLoader {
    private static final int COUNT_PER_REQUEST = 200;

    private ApiService apiService;
    private WebLoader webLoader;

    public GalleryWebLoader(ApiService apiService, WebLoader webLoader) {
        this.apiService = apiService;
        this.webLoader = webLoader;
    }

    @Override
    @NonNull
    public Gallery load(@NonNull GalleryParameters parameters) throws IOException {
        Gallery gallery = webLoader.load(apiService.getCallGallery(parameters.getUserId(),
                true, parameters.getOffset(), COUNT_PER_REQUEST));
        gallery.sort();

        return gallery;
    }
}
