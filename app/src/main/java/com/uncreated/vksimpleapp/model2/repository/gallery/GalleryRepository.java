package com.uncreated.vksimpleapp.model2.repository.gallery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.uncreated.vksimpleapp.model2.entity.vk.Gallery;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class GalleryRepository {

    private GalleryLoader galleryWebLoader;
    private GalleryLoader galleryStorageLoader;

    public GalleryRepository(GalleryLoader galleryWebLoader, GalleryLoader galleryStorageLoader) {
        this.galleryWebLoader = galleryWebLoader;
        this.galleryStorageLoader = galleryStorageLoader;
    }

    public Observable<Gallery> loadFromWeb(@NonNull String userId) {
        return load(userId, galleryWebLoader, galleryStorageLoader);
    }

    public Observable<Gallery> loadFromStorage(@NonNull String userId) {
        return load(userId, galleryStorageLoader, null);
    }

    private Observable<Gallery> load(@NonNull String userId, @NonNull GalleryLoader galleryLoader,
                                     @Nullable GalleryLoader gallerySaver) {
        return Observable.create((ObservableOnSubscribe<Gallery>) emitter -> {
            {
                Gallery gallery = null;
                int offset = 0;
                do {
                    GalleryParameters parameters = new GalleryParameters(userId, offset);
                    gallery = new Gallery(gallery, galleryLoader.load(parameters));
                    emitter.onNext(gallery);
                    if (gallerySaver != null) {
                        gallerySaver.save(parameters, gallery);
                    }
                    if (emitter.isDisposed()) {
                        break;
                    }
                } while ((offset = gallery.getCurrentSize()) < gallery.getTotalCount());
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }
}
