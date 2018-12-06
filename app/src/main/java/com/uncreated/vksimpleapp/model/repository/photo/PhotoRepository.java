package com.uncreated.vksimpleapp.model.repository.photo;

import android.graphics.Bitmap;

import com.uncreated.vksimpleapp.model.repository.photo.loader.IPhotoLoader;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import io.reactivex.Single;

public class PhotoRepository implements IPhotoRepository {

    private IPhotoLoader photoLoader;
    private GalleryPhotoCache photoCache;

    public PhotoRepository(IPhotoLoader photoLoader,
                           GalleryPhotoCache photoCache) {
        this.photoLoader = photoLoader;
        this.photoCache = photoCache;
    }

    public Single<Bitmap> getBitmapObservable(String url) {
        return Single.create(emitter -> emitter.onSuccess(photoLoader.loadToCache(url, photoCache)));
    }
}
