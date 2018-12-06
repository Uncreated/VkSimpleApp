package com.uncreated.vksimpleapp.model.repository.photo;

import com.uncreated.vksimpleapp.model.entity.events.IndexedBitmap;
import com.uncreated.vksimpleapp.model.entity.events.IndexedUrl;
import com.uncreated.vksimpleapp.model.repository.photo.loader.IPhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import io.reactivex.Single;

public class PhotoRepository implements IPhotoRepository {

    private IPhotoLoader photoLoader;
    private GalleryPhotoCache thumbnailsCache;
    private GalleryPhotoCache originalsCache;

    //TODO:divide into orig and thumb repo
    public PhotoRepository(IPhotoLoader photoLoader,
                           GalleryPhotoCache thumbnailsCache,
                           GalleryPhotoCache originalsCache) {
        this.photoLoader = photoLoader;
        this.thumbnailsCache = thumbnailsCache;
        this.originalsCache = originalsCache;
    }

    public Single<IndexedBitmap> getIndexedBitmapObservable(IndexedUrl url, boolean thumbnail) {
        return Single.create(emitter -> emitter.onSuccess(photoLoader.loadToCache(url,
                thumbnail ? thumbnailsCache : originalsCache)));
    }
}
