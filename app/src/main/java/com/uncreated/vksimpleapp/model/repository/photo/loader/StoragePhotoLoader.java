package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.request.FutureTarget;
import com.uncreated.vksimpleapp.model.common.Utils;
import com.uncreated.vksimpleapp.model.entity.events.IndexedBitmap;
import com.uncreated.vksimpleapp.model.entity.events.IndexedUrl;
import com.uncreated.vksimpleapp.model.entity.realm.RealmPhoto;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;

public class StoragePhotoLoader extends WebPhotoLoader {

    private static final String STORAGE_FOLDER = "images";

    private Context context;

    public StoragePhotoLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public IndexedBitmap loadToCache(IndexedUrl indexedUrl, GalleryPhotoCache galleryPhotoCache) throws Exception {
        IndexedBitmap indexedBitmap = null;

        Realm realm = Realm.getDefaultInstance();
        RealmPhoto realmPhoto = realm.where(RealmPhoto.class)
                .equalTo("url", indexedUrl.getUrl())
                .findFirst();

        if (realmPhoto != null) {
            try {
                File file = new File(realmPhoto.getPath());
                if (file.exists()) {
                    FutureTarget<Bitmap> futureBitmap = GlideApp.with(context)
                            .asBitmap()
                            .load(file)
                            .submit();
                    Bitmap bitmap = futureBitmap.get();
                    indexedBitmap = new IndexedBitmap(bitmap, indexedUrl.getIndex());
                    galleryPhotoCache.putBitmap(indexedUrl.getIndex(), indexedBitmap.getBitmap());
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (indexedBitmap == null) {
            //super if not
            indexedBitmap = super.loadToCache(indexedUrl, galleryPhotoCache);

            String path = generatePath(indexedUrl.getUrl());
            if (saveToFile(indexedBitmap.getBitmap(), path)) {
                realm.executeTransaction(innerRealm -> {
                    RealmPhoto newRealmPhoto = realm.createObject(RealmPhoto.class, indexedUrl.getUrl());
                    newRealmPhoto.setPath(generatePath(indexedUrl.getUrl()));
                });
            }
        }
        return indexedBitmap;
    }

    private boolean saveToFile(Bitmap bitmap, String path) {
        File file = new File(path);
        boolean b = file.getParentFile().mkdirs();

        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String generatePath(String url) {
        return context.getFilesDir().getAbsolutePath() +
                STORAGE_FOLDER + "/"
                + Utils.MD5(url) + ".jpg";
    }
}
