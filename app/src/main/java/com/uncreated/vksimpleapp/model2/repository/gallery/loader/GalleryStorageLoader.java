package com.uncreated.vksimpleapp.model2.repository.gallery.loader;

import com.uncreated.vksimpleapp.model.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryLoader;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryParameters;

import java.io.IOException;

import io.realm.Realm;

public class GalleryStorageLoader extends GalleryLoader {

    private static final String PRIMARY_KEY = "userId";

    @Override
    public void save(GalleryParameters parameters, Gallery gallery) throws IOException {
        super.save(parameters, gallery);

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
    public Gallery load(GalleryParameters parameters) throws IOException {
        Realm realm = Realm.getDefaultInstance();

        RealmGallery realmGallery = realm.where(RealmGallery.class)
                .equalTo(PRIMARY_KEY, parameters.getUserId())
                .findFirst();

        if (realmGallery != null) {
            return new Gallery(realmGallery);
        } else {
            return null;
        }
    }
}
