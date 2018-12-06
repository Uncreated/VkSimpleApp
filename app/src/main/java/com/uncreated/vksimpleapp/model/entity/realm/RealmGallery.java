package com.uncreated.vksimpleapp.model.entity.realm;

import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGallery extends RealmObject {
    @PrimaryKey
    private String userId;

    private int count;

    private RealmList<RealmPhotoInfo> items;

    public RealmGallery() {
    }

    public RealmGallery(Gallery gallery, String userId) {
        this.userId = userId;
        this.count = gallery.getTotalCount();
        this.items = new RealmList<>();
        for (PhotoInfo photoInfo : gallery.getItems()) {
            this.items.add(new RealmPhotoInfo(photoInfo));
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public RealmList<RealmPhotoInfo> getItems() {
        return items;
    }

    public void setItems(RealmList<RealmPhotoInfo> items) {
        this.items = items;
    }
}
