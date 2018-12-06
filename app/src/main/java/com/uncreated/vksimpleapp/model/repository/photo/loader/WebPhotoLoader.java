package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

public class WebPhotoLoader implements IPhotoLoader {

    private Context context;

    public WebPhotoLoader(Context context) {
        this.context = context;
    }

    //TODO:make it cancelable
    @Override
    public Bitmap loadToCache(String url, GalleryPhotoCache galleryPhotoCache) throws Exception {
        return GlideApp.with(context)
                .asBitmap()
                .load(url)
                .submit()
                .get();
    }
}
