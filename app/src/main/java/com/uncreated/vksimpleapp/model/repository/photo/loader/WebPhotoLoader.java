package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.request.FutureTarget;
import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public class WebPhotoLoader implements IPhotoLoader {

    private Context context;

    public WebPhotoLoader(Context context) {
        this.context = context;
    }

    @Override
    public BitmapIndex loadToCache(IndexUrl indexUrl,
                                   GalleryPhotoCache galleryPhotoCache) throws Exception {

        FutureTarget<Bitmap> futureBitmap = GlideApp.with(context)
                .asBitmap()
                .load(indexUrl.getUrl())
                .submit();

        Bitmap bitmap = futureBitmap.get();
        galleryPhotoCache.putBitmap(indexUrl.getIndex(), bitmap);

        return new BitmapIndex(bitmap, indexUrl.getIndex());
    }
}
