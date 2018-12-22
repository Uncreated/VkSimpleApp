package com.uncreated.vksimpleapp.model2.entity.vk;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.entity.realm.RealmGallery;
import com.uncreated.vksimpleapp.model2.entity.realm.RealmPhotoInfo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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

    public Gallery(@Nullable Gallery partOne, @NonNull Gallery partTwo) {
        totalCount = partTwo.totalCount;
        items = new ArrayList<>(partTwo.items.size() + partTwo.items.size());
        if (partOne != null) {
            items.addAll(partOne.items);
        }
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
