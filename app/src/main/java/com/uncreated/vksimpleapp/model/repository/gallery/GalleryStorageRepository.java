package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class GalleryStorageRepository extends GalleryWebRepository {

    public GalleryStorageRepository(ApiService apiService, EventBus eventBus) {
        super(apiService, eventBus);
    }

    @Override
    protected void applyEventBus(ApiService apiService, EventBus eventBus) {
        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(Schedulers.io())
                .subscribe(this::saveGallery));

        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(Schedulers.io())
                .subscribe(user -> getGallery(apiService, eventBus, user)
                        .subscribe(gallery -> eventBus.getGallerySubject().onNext(gallery),
                                throwable -> {
                                    Gallery gallery = getGalleryFromStorage(user);
                                    if (gallery != null) {
                                        user.setGallery(gallery);
                                        eventBus.getGallerySubject().onNext(gallery);
                                    } else {
                                        throw new RuntimeException("Can't load gallery");
                                    }
                                })));
    }

    private void saveGallery(Gallery gallery) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(innerRealm -> {
            realm.where(RealmGallery.class)
                    .equalTo("userId", gallery.getUser().getId())
                    .findAll()
                    .deleteAllFromRealm();

            RealmGallery realmGallery = new RealmGallery(gallery);
            realm.copyToRealm(realmGallery);
        });
    }

    private Gallery getGalleryFromStorage(User user) {
        Realm realm = Realm.getDefaultInstance();
        RealmGallery realmGallery = realm.where(RealmGallery.class)
                .equalTo("userId", user.getId())
                .findFirst();

        if (realmGallery != null) {
            return new Gallery(user, realmGallery);
        } else {
            return null;
        }
    }
}
