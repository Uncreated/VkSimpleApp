package com.uncreated.vksimpleapp.model.entity.vk;

import com.uncreated.vksimpleapp.model.entity.realm.RealmUser;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String photoMax;

    public User(RealmUser realmUser) {
        this.id = realmUser.getUserId();
        this.firstName = realmUser.getFirstName();
        this.lastName = realmUser.getLastName();
        this.photoMax = realmUser.getPhotoUrl();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoUrl() {
        return photoMax;
    }
}
