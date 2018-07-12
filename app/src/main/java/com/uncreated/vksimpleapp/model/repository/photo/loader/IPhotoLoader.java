package com.uncreated.vksimpleapp.model.repository.photo.loader;

import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public interface IPhotoLoader {
    BitmapIndex loadToCache(IndexUrl indexUrl, GalleryPhotoCache galleryPhotoCache) throws Exception;
}
