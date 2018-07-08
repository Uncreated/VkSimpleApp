package com.uncreated.vksimpleapp.model.repository.photo.ram;

import android.graphics.Bitmap;
import android.util.LruCache;

public class GalleryPhotoCache {

    private LruCache<Integer, Bitmap> items;

    public GalleryPhotoCache(int itemsCount) {
        items = new LruCache<>(itemsCount);
    }

    public void putBitmap(int index, Bitmap bitmap) {
        items.put(index, bitmap);
    }

    public Bitmap getBitmap(int index) {
        return items.get(index);
    }

    public void clear() {
        items.evictAll();
    }
}
