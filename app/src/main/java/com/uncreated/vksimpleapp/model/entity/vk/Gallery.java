package com.uncreated.vksimpleapp.model.entity.vk;

import com.uncreated.vksimpleapp.model.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model.entity.realm.RealmPhotoInfo;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

    private int count;
    private List<PhotoInfo> items;

    public Gallery(RealmGallery realmGallery) {
        this.count = realmGallery.getCount();
        this.items = new ArrayList<>(realmGallery.getItems().size());
        for (RealmPhotoInfo realmPhotoInfo : realmGallery.getItems()) {
            this.items.add(new PhotoInfo(realmPhotoInfo));
        }
    }

    public int getCount() {
        return count;
    }

    public List<PhotoInfo> getItems() {
        return items;
    }

    public int getCurrentSize() {
        return items.size();
    }

    public void sort() {
        for (PhotoInfo photoInfo : items) {
            photoInfo.sort();
        }
    }
}
