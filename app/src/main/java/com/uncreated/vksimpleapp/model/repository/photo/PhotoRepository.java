package com.uncreated.vksimpleapp.model.repository.photo;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.repository.photo.loader.IPhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import io.reactivex.schedulers.Schedulers;

public class PhotoRepository implements IPhotoRepository {

    public PhotoRepository(EventBus eventBus,
                           IPhotoLoader webPhotoLoader,
                           GalleryPhotoCache thumbnailsCache,
                           GalleryPhotoCache originalsCache) {

        EventBus.BitmapEvents thumbnailEvents = eventBus.getThumbnailEvents();
        EventBus.BitmapEvents originalEvents = eventBus.getOriginalEvents();

        thumbnailEvents.getUrlSubject()
                .observeOn(Schedulers.io())
                .map(indexUrl -> webPhotoLoader.loadToCache(indexUrl, thumbnailsCache))
                .subscribe(thumbnailEvents.getBitmapSubject());

        originalEvents.getUrlSubject()
                .observeOn(Schedulers.io())
                .map(indexUrl -> webPhotoLoader.loadToCache(indexUrl, originalsCache))
                .subscribe(originalEvents.getBitmapSubject());
    }
}
