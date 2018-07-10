package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.request.FutureTarget;
import com.uncreated.vksimpleapp.model.common.Utils;
import com.uncreated.vksimpleapp.model.entity.realm.RealmPhoto;
import com.uncreated.vksimpleapp.model.repository.BitmapIndex;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;
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
    public BitmapIndex loadToCache(IndexUrl indexUrl, GalleryPhotoCache galleryPhotoCache) throws Exception {
        BitmapIndex bitmapIndex = null;

        Realm realm = Realm.getDefaultInstance();
        RealmPhoto realmPhoto = realm.where(RealmPhoto.class)
                .equalTo("url", indexUrl.getUrl())
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
                    bitmapIndex = new BitmapIndex(bitmap, indexUrl.getIndex());
                    galleryPhotoCache.putBitmap(indexUrl.getIndex(), bitmapIndex.getBitmap());
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (bitmapIndex == null) {
            //super if not
            bitmapIndex = super.loadToCache(indexUrl, galleryPhotoCache);

            String path = generatePath(indexUrl.getUrl());
            if (saveToFile(bitmapIndex.getBitmap(), path)) {
                realm.executeTransaction(innerRealm -> {
                    RealmPhoto newRealmPhoto = realm.createObject(RealmPhoto.class, indexUrl.getUrl());
                    newRealmPhoto.setPath(generatePath(indexUrl.getUrl()));
                });
            }
        }
        return bitmapIndex;
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
