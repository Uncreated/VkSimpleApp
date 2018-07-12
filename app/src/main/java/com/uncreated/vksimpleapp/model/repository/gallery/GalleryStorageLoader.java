package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;

import io.realm.Realm;

public class GalleryStorageLoader {
    private static final String PRIMARY_KEY = "userId";

    void saveGallery(Gallery gallery, String userId) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(innerRealm -> {
            realm.where(RealmGallery.class)
                    .equalTo(PRIMARY_KEY, userId)
                    .findAll()
                    .deleteAllFromRealm();

            RealmGallery realmGallery = new RealmGallery(gallery, userId);
            realm.copyToRealm(realmGallery);
        });
    }

    Gallery loadGallery(String userId) {
        Realm realm = Realm.getDefaultInstance();

        RealmGallery realmGallery = realm.where(RealmGallery.class)
                .equalTo(PRIMARY_KEY, userId)
                .findFirst();

        if (realmGallery != null) {
            return new Gallery(realmGallery);
        } else {
            return null;
        }
    }
}
