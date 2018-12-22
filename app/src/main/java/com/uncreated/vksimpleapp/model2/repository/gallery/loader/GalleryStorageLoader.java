package com.uncreated.vksimpleapp.model2.repository.gallery.loader;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model2.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryParameters;

import java.io.IOException;

import io.realm.Realm;

public class GalleryStorageLoader extends GalleryLoader {

    private static final String PRIMARY_KEY = "userId";

    @Override
    public void save(@NonNull GalleryParameters parameters, @NonNull Gallery gallery) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(innerRealm -> {
            realm.where(RealmGallery.class)
                    .equalTo(PRIMARY_KEY, parameters.getUserId())
                    .findAll()
                    .deleteAllFromRealm();

            realm.copyToRealm(new RealmGallery(gallery, parameters.getUserId()));
        });

    }

    @Override
    @NonNull
    public Gallery load(@NonNull GalleryParameters parameters) throws IOException {
        RealmGallery realmGallery = Realm.getDefaultInstance()
                .where(RealmGallery.class)
                .equalTo(PRIMARY_KEY, parameters.getUserId())
                .findFirst();
        if (realmGallery == null) {
            throw new IOException("Gallery not found in storage: " + parameters);
        }
        return new Gallery(realmGallery);
    }
}
