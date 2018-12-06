package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.graphics.Bitmap;

import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public interface IPhotoLoader {
    Bitmap loadToCache(String url, GalleryPhotoCache galleryPhotoCache) throws Exception;
}
