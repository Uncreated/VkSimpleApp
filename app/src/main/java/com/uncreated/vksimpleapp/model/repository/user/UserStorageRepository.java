package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.realm.RealmUser;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class UserStorageRepository extends UserWebRepository {

    public UserStorageRepository(ApiService apiService, EventBus eventBus) {
        super(apiService, eventBus);
    }

    @Override
    protected void applyEventBus(ApiService apiService, EventBus eventBus) {
        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(Schedulers.io())
                .subscribe(this::saveUser));

        compositeDisposable.add(eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .subscribe(auth -> getUser(apiService, eventBus, auth)
                        .observeOn(Schedulers.io())
                        .subscribe(user -> eventBus.getUserSubject().onNext(user),
                                throwable -> {
                                    User user = getUserFromStorage(auth);
                                    if (user != null) {
                                        eventBus.getUserSubject().onNext(user);
                                    } else {
                                        throw new RuntimeException("Can't load user");
                                    }
                                })));
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(innerRealm -> {
            RealmUser realmUser = realm.where(RealmUser.class)
                    .equalTo("userId", user.getId())
                    .findFirst();
            if (realmUser == null) {
                realmUser = realm.createObject(RealmUser.class, user.getId());
            }
            realmUser.setFirstName(user.getFirstName());
            realmUser.setLastName(user.getLastName());
            realmUser.setPhotoUrl(user.getPhotoUrl());
        });
    }

    private User getUserFromStorage(Auth auth) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class)
                .equalTo("userId", auth.getUserId())
                .findFirst();

        if (realmUser != null) {
            return new User(realmUser.getUserId(),
                    realmUser.getFirstName(),
                    realmUser.getLastName(),
                    realmUser.getPhotoUrl());
        } else {
            return null;
        }
    }
}
