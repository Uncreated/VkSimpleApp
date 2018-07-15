package com.uncreated.vksimpleapp.model.repository.photo;

import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.photo.loader.IPhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoRepository implements IPhotoRepository {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PhotoRepository(EventBus eventBus,
                           IPhotoLoader photoLoader,
                           GalleryPhotoCache thumbnailsCache,
                           GalleryPhotoCache originalsCache) {
        compositeDisposable.add(eventBus.thumbnailIndexUrlSubscribe(indexUrl -> {
            BitmapIndex bitmapIndex = photoLoader.loadToCache(indexUrl, thumbnailsCache);
            eventBus.thumbnailEventsPost(bitmapIndex);
        }, Schedulers.io()));


        compositeDisposable.add(eventBus.originalIndexUrlSubscribe(indexUrl -> {
            BitmapIndex bitmapIndex = photoLoader.loadToCache(indexUrl, originalsCache);
            eventBus.originalEventPost(bitmapIndex);
        }, Schedulers.io()));
    }
}
