package com.uncreated.vksimpleapp.model.repository.photo.loader;

import com.uncreated.vksimpleapp.model.entity.events.IndexedBitmap;
import com.uncreated.vksimpleapp.model.entity.events.IndexedUrl;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public interface IPhotoLoader {
    IndexedBitmap loadToCache(IndexedUrl indexedUrl, GalleryPhotoCache galleryPhotoCache) throws Exception;
}
