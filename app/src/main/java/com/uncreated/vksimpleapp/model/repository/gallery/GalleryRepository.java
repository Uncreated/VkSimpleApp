package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GalleryRepository {

    private GalleryWebLoader galleryWebLoader;
    private GalleryStorageLoader galleryStorageLoader;

    public GalleryRepository(EventBus eventBus,
                             GalleryWebLoader galleryWebLoader,
                             GalleryStorageLoader galleryStorageLoader) {
        this.galleryWebLoader = galleryWebLoader;
        this.galleryStorageLoader = galleryStorageLoader;

        eventBus.userSubscribe(user -> loadGallery(eventBus, user.getId()), Schedulers.io());
    }

    private void loadGallery(EventBus eventBus, String userId) {
        Gallery gallery = null;

        try {
            int offset = 0;
            do {
                Gallery galleryPart = galleryWebLoader.loadGallery(userId, offset);
                if (gallery != null) {
                    gallery.getItems().addAll(galleryPart.getItems());
                } else {
                    gallery = galleryPart;
                }
                offset = gallery.getItems().size();
                eventBus.galleryPost(gallery);
                galleryStorageLoader.saveGallery(gallery, userId);
            }
            while (offset < gallery.getCount());
        } catch (RequestException e) {
            if (!galleryWebLoader.handleVkError(eventBus, e.getRequestError())) {
                loadFromStorage(eventBus, gallery, userId);
            }
        } catch (Exception e) {
            Timber.tag("MyDebug").d(e);

            loadFromStorage(eventBus, gallery, userId);
        }
    }

    private void loadFromStorage(EventBus eventBus, Gallery gallery, String userId) {
        if (gallery == null) {
            gallery = galleryStorageLoader.loadGallery(userId);
            if (gallery != null) {
                eventBus.galleryPost(gallery);
            }
        }
    }
}
