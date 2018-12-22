package com.uncreated.vksimpleapp.model2.repository.user.loader;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.entity.realm.RealmUser;
import com.uncreated.vksimpleapp.model2.entity.vk.User;
import com.uncreated.vksimpleapp.model2.repository.user.UserLoader;
import com.uncreated.vksimpleapp.model2.repository.user.UserParameters;

import java.io.IOException;

import io.realm.Realm;

public class UserStorageLoader extends UserLoader {
    private static final String PRIMARY_KEY = "userId";

    @NonNull
    @Override
    public User load(@NonNull UserParameters parameters) throws IOException {
        Realm realm = Realm.getDefaultInstance();

        RealmUser realmUser = realm.where(RealmUser.class)
                .equalTo(PRIMARY_KEY, parameters.getUserId())
                .findFirst();

        if (realmUser == null) {
            throw new IOException("User not found: " + parameters);
        }

        return new User(realmUser);
    }

    @Override
    public void save(@NonNull UserParameters userParameters, @NonNull User user) {
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
}
