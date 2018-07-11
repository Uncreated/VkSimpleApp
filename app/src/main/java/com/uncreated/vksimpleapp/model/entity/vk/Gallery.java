package com.uncreated.vksimpleapp.model.entity.vk;

import com.uncreated.vksimpleapp.model.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model.entity.realm.RealmPhotoInfo;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

    private User user;

    private int count;
    private List<PhotoInfo> items;

    public Gallery(int count, List<PhotoInfo> items) {
        this.count = count;
        this.items = items;
    }

    public Gallery(User user, RealmGallery realmGallery) {
        this.user = user;
        this.count = realmGallery.getCount();
        this.items = new ArrayList<>(realmGallery.getItems().size());
        for (RealmPhotoInfo realmPhotoInfo : realmGallery.getItems()) {
            this.items.add(new PhotoInfo(realmPhotoInfo));
        }
    }

    public int getSize() {
        return count;
    }

    public List<PhotoInfo> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sort() {
        for (PhotoInfo photoInfo : items) {
            photoInfo.sort();
        }
    }
}
