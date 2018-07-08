package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WebGalleryRepository implements IGalleryRepository {

    private ApiService apiService;

    public WebGalleryRepository(ApiService apiService, EventBus eventBus) {
        this.apiService = apiService;

        Disposable disposable = eventBus.getGallerySubject()
                .observeOn(Schedulers.io())
                .subscribe(gallery -> {
                    if (gallery.getItems().size() < gallery.getSize()) {
                        int offset = gallery.getItems().size();
                        Gallery nextGallery = getGallery(gallery.getUser(), offset);

                        gallery.getItems().addAll(nextGallery.getItems());
                        eventBus.getGallerySubject()
                                .onNext(gallery);
                    }
                });

        eventBus.getUserSubject()
                .observeOn(Schedulers.io())
                .map(user -> {
                    Gallery gallery = getGallery(user, 0);
                    user.setGallery(gallery);
                    return gallery;
                })
                .subscribe(eventBus.getGallerySubject());
    }

    private Gallery getGallery(User user, int offset) throws Exception {
        VkResponse<Gallery> galleryResponse =
                apiService.getGallery(user.getId(), true, offset, 200)
                        .execute()
                        .body();

        Gallery gallery = galleryResponse.getResponse();
        if (gallery == null) {
            throw new RequestException(galleryResponse.getRequestError());
        }
        gallery.sort();
        return gallery;
    }
}
