package com.uncreated.vksimpleapp.model.repository.photo.ram;

import android.graphics.Bitmap;
import android.util.LruCache;

public class GalleryPhotoCache {

    private LruCache<String, Bitmap> items;

    public GalleryPhotoCache(int itemsCount) {
        items = new LruCache<>(itemsCount);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        items.put(url, bitmap);
    }

    public Bitmap getBitmap(String url) {
        return items.get(url);
    }

    public void clear() {
        items.evictAll();
    }
}
