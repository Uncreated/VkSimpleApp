package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WebGalleryRepository implements IGalleryRepository {

    private ApiService apiService;

    public WebGalleryRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Gallery> get(String userId) {
        return apiService.getGallery(userId, true, 0, 200)
                .subscribeOn(Schedulers.io())
                .map(galleryVkResponse -> {
                    Gallery gallery = galleryVkResponse.getResponse();
                    if (gallery == null) {
                        throw new RequestException(galleryVkResponse.getRequestError());
                    }
                    gallery.sort();
                    return gallery;
                });
    }
}
