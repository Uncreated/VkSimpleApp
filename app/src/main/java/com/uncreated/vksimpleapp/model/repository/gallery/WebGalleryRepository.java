package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WebGalleryRepository implements IGalleryRepository {

    private ApiService apiService;

    public WebGalleryRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Gallery> get(User user) {
        return apiService.getGallery(user.getId(), true, 2)
                .subscribeOn(Schedulers.io())
                .map(galleryVkResponse -> {
                    Gallery gallery = galleryVkResponse.getResponse();
                    if (gallery != null) {
                        return gallery;
                    } else {
                        throw new RequestException(galleryVkResponse.getRequestError());
                    }
                });
    }

    @Override
    public void set(User user, Gallery gallery) {
        //nothing
    }
}
