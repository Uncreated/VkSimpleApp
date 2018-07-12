package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.entity.realm.RealmUser;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.realm.Realm;

public class UserStorageLoader {
    private static final String PRIMARY_KEY = "userId";

    public void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(innerRealm -> {
            RealmUser realmUser = realm.where(RealmUser.class)
                    .equalTo(PRIMARY_KEY, user.getId())
                    .findFirst();
            if (realmUser == null) {
                realmUser = realm.createObject(RealmUser.class, user.getId());
            }
            realmUser.setFirstName(user.getFirstName());
            realmUser.setLastName(user.getLastName());
            realmUser.setPhotoUrl(user.getPhotoUrl());
        });
    }

    public User loadUser(String userId) {
        Realm realm = Realm.getDefaultInstance();

        RealmUser realmUser = realm.where(RealmUser.class)
                .equalTo(PRIMARY_KEY, userId)
                .findFirst();

        if (realmUser != null) {
            return new User(realmUser);
        } else {
            return null;
        }
    }
}
