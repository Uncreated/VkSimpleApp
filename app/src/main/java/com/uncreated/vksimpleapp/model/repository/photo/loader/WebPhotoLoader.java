package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.request.FutureTarget;
import com.uncreated.vksimpleapp.model.entity.events.IndexedBitmap;
import com.uncreated.vksimpleapp.model.entity.events.IndexedUrl;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public class WebPhotoLoader implements IPhotoLoader {

    private Context context;

    public WebPhotoLoader(Context context) {
        this.context = context;
    }

    @Override
    public IndexedBitmap loadToCache(IndexedUrl indexedUrl,
                                     GalleryPhotoCache galleryPhotoCache) throws Exception {

        FutureTarget<Bitmap> futureBitmap = GlideApp.with(context)
                .asBitmap()
                .load(indexedUrl.getUrl())
                .submit();

        Bitmap bitmap = futureBitmap.get();
        galleryPhotoCache.putBitmap(indexedUrl.getIndex(), bitmap);

        return new IndexedBitmap(bitmap, indexedUrl.getIndex());
    }
}
