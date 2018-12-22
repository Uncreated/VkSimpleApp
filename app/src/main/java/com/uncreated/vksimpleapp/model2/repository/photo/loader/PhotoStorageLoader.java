package com.uncreated.vksimpleapp.model2.repository.photo.loader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.common.GlideApp;
import com.uncreated.vksimpleapp.model2.common.Utils;
import com.uncreated.vksimpleapp.model2.entity.realm.RealmPhoto;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoLoader;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import timber.log.Timber;

public class PhotoStorageLoader extends PhotoLoader {

    private static final String STORAGE_FOLDER = "images";

    @Override
    @NonNull
    public Bitmap load(@NonNull PhotoParameters photoParameters) throws IOException {

        Realm realm = Realm.getDefaultInstance();
        RealmPhoto realmPhoto = realm.where(RealmPhoto.class)
                .equalTo("url", photoParameters.getUrl())
                .findFirst();

        if (realmPhoto != null) {
            File file = new File(realmPhoto.getPath());
            if (file.exists()) {
                try {
                    return GlideApp.with(photoParameters.getContext())
                            .asBitmap()
                            .load(file)
                            .submit()
                            .get();
                } catch (ExecutionException | InterruptedException e) {
                    Timber.e(e);
                }
            }
        }
        throw new IOException("Photo not found: " + photoParameters);
    }

    @Override
    public void save(@NonNull PhotoParameters photoParameters, @NonNull Bitmap bitmap) {
        String path = convertToPath(photoParameters);
        if (new File(path).getParentFile().mkdirs()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            } catch (IOException e) {
                Timber.e(e);
            }
        }
    }

    private String convertToPath(@NonNull PhotoParameters photoParameters) {
        String absolutePath = photoParameters.getContext()
                .getFilesDir()
                .getAbsolutePath();

        return absolutePath + STORAGE_FOLDER + "/" + Utils.MD5(photoParameters.getUrl()) + ".jpg";
    }
}
