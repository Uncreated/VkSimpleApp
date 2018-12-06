package com.uncreated.vksimpleapp.model.repository.photo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.request.FutureTarget;
import com.uncreated.vksimpleapp.model.common.Utils;
import com.uncreated.vksimpleapp.model.entity.realm.RealmPhoto;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;

//TODO:replace extends with inject
public class StoragePhotoLoader extends WebPhotoLoader {

    private static final String STORAGE_FOLDER = "images";

    private Context context;

    public StoragePhotoLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bitmap loadToCache(String url, GalleryPhotoCache galleryPhotoCache) throws Exception {
        Bitmap bitmap = null;

        Realm realm = Realm.getDefaultInstance();
        RealmPhoto realmPhoto = realm.where(RealmPhoto.class)
                .equalTo("url", url)
                .findFirst();

        if (realmPhoto != null) {
            try {
                File file = new File(realmPhoto.getPath());
                if (file.exists()) {
                    FutureTarget<Bitmap> futureBitmap = GlideApp.with(context)
                            .asBitmap()
                            .load(file)
                            .submit();
                    bitmap = futureBitmap.get();
                    galleryPhotoCache.putBitmap(url, bitmap);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (bitmap == null) {
            bitmap = super.loadToCache(url, galleryPhotoCache);

            String path = generatePath(url);
            if (saveToFile(bitmap, path)) {
                realm.executeTransaction(innerRealm -> {
                    RealmPhoto newRealmPhoto = realm.createObject(RealmPhoto.class, url);
                    newRealmPhoto.setPath(generatePath(url));
                });
            }
        }
        return bitmap;
    }

    private boolean saveToFile(Bitmap bitmap, String path) {
        if (new File(path).getParentFile().mkdirs()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    private String generatePath(String url) {
        return context.getFilesDir().getAbsolutePath() +
                STORAGE_FOLDER + "/"
                + Utils.MD5(url) + ".jpg";
    }
}
