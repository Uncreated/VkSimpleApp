package com.uncreated.vksimpleapp.model2.repository.photo;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PhotoRepository {

    private PhotoLoader photoWebLoader;
    private PhotoLoader photoStorageLoader;
    private PhotoLoader photoRamLoader;

    public PhotoRepository(PhotoLoader photoWebLoader, PhotoLoader photoStorageLoader, PhotoLoader photoRamLoader) {
        this.photoWebLoader = photoWebLoader;
        this.photoStorageLoader = photoStorageLoader;
        this.photoRamLoader = photoRamLoader;
    }

    @NonNull
    public Single<Bitmap> load(@NonNull PhotoParameters photoParameters) {
        return Single.create((SingleOnSubscribe<Bitmap>) emitter -> {
            Bitmap bitmap;
            try {
                bitmap = photoRamLoader.load(photoParameters);
            } catch (IOException e) {
                Timber.d(e);
                try {
                    bitmap = photoStorageLoader.load(photoParameters);
                } catch (IOException e1) {
                    Timber.d(e1);
                    try {
                        bitmap = photoWebLoader.load(photoParameters);
                        photoStorageLoader.save(photoParameters, bitmap);
                    } catch (IOException e2) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e2);
                        }
                        return;
                    }
                }
                photoRamLoader.save(photoParameters, bitmap);
            }
            emitter.onSuccess(bitmap);
        }).subscribeOn(Schedulers.io());
    }
}
