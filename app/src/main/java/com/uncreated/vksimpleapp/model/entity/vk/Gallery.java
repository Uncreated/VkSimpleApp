package com.uncreated.vksimpleapp.model.entity.vk;

import com.uncreated.vksimpleapp.model.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model.entity.realm.RealmPhotoInfo;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

    private int totalCount;
    private List<PhotoInfo> items;

    public Gallery(RealmGallery realmGallery) {
        totalCount = realmGallery.getCount();
        items = new ArrayList<>(realmGallery.getItems().size());
        for (RealmPhotoInfo realmPhotoInfo : realmGallery.getItems()) {
            items.add(new PhotoInfo(realmPhotoInfo));
        }
    }

    public Gallery(Gallery partOne, Gallery partTwo) {
        totalCount = partOne.totalCount;
        items = new ArrayList<>(partOne.items.size() + partTwo.items.size());
        items.addAll(partOne.items);
        items.addAll(partTwo.items);
    }

    public int getTotalCount() {
        return totalCount;
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
