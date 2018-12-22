package com.uncreated.vksimpleapp.model2.repository.photo.loader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.LruCache;

import com.uncreated.vksimpleapp.model2.repository.photo.PhotoLoader;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoParameters;

import java.io.IOException;

public class PhotoRamLoader extends PhotoLoader {

    private LruCache<String, Bitmap> items;

    public PhotoRamLoader(int itemsCount) {
        items = new LruCache<>(itemsCount);
    }

    @NonNull
    @Override
    public Bitmap load(@NonNull PhotoParameters photoParameters) throws IOException {
        Bitmap bitmap = items.get(photoParameters.getUrl());
        if (bitmap == null) {
            throw new IOException("Photo not found" + photoParameters);
        }
        return bitmap;
    }

    @Override
    public void save(@NonNull PhotoParameters photoParameters, @NonNull Bitmap bitmap) {
        items.put(photoParameters.getUrl(), bitmap);
    }
}
