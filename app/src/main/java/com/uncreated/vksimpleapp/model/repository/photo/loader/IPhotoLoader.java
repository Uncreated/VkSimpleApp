package com.uncreated.vksimpleapp.model.repository.photo.loader;

import com.uncreated.vksimpleapp.model.repository.BitmapIndex;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public interface IPhotoLoader {
    BitmapIndex loadToCache(IndexUrl indexUrl, GalleryPhotoCache galleryPhotoCache) throws Exception;
}
