package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.entity.vk.Gallery;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import timber.log.Timber;

public class GalleryRepository {

    private GalleryWebLoader galleryWebLoader;
    private GalleryStorageLoader galleryStorageLoader;

    public GalleryRepository(GalleryWebLoader galleryWebLoader,
                             GalleryStorageLoader galleryStorageLoader) {
        this.galleryWebLoader = galleryWebLoader;
        this.galleryStorageLoader = galleryStorageLoader;
    }

    public Observable<Gallery> getGalleryObservable(String userId) {
        return Observable.create(emitter -> {
            Gallery gallery = null;

            try {
                int offset = 0;
                do {
                    Gallery galleryPart = galleryWebLoader.loadGallery(userId, offset);
                    if (gallery != null) {
                        gallery = new Gallery(gallery, galleryPart);
                    } else {
                        gallery = galleryPart;
                    }
                    offset = gallery.getCurrentSize();
                    emitter.onNext(gallery);
                    galleryStorageLoader.saveGallery(gallery, userId);
                }
                while (offset < gallery.getTotalCount());
            } catch (Exception e) {
                Timber.d(e);

                loadFromStorage(emitter, userId);
            }
            emitter.onComplete();
        });
    }

    private void loadFromStorage(Emitter<Gallery> emitter, String userId) {
        emitter.onNext(galleryStorageLoader.loadGallery(userId));
    }
}
