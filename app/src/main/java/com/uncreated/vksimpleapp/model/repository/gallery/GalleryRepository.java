package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;

import io.reactivex.disposables.Disposable;
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

        Disposable disposable = eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .subscribe(auth -> newAuth(eventBus, auth));
    }

    private void newAuth(EventBus eventBus, Auth auth) {
        Gallery gallery = null;
        String userId = auth.getUserId();

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
                eventBus.getGallerySubject()
                        .onNext(gallery);
                galleryStorageLoader.saveGallery(gallery, userId);
            }
            while (offset < gallery.getSize());
        } catch (Exception e) {
            Timber.tag("MyDebug").d(e);

            if (gallery == null) {
                gallery = galleryStorageLoader.loadGallery(userId);
                eventBus.getGallerySubject()
                        .onNext(gallery);
            }
        }
    }
}
