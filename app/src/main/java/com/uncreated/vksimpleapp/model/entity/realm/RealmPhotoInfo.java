package com.uncreated.vksimpleapp.model.entity.realm;

import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoSize;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmPhotoInfo extends RealmObject {
    private Long id;
    private Long ownerId;
    private RealmList<RealmPhotoSize> sizes;

    public RealmPhotoInfo() {
    }

    RealmPhotoInfo(PhotoInfo photoInfo) {
        this.id = photoInfo.getId();
        this.ownerId = photoInfo.getOwnerId();
        this.sizes = new RealmList<>();
        for (PhotoSize photoSize : photoInfo.getSizes()) {
            this.sizes.add(new RealmPhotoSize(photoSize));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public RealmList<RealmPhotoSize> getSizes() {
        return sizes;
    }

    public void setSizes(RealmList<RealmPhotoSize> sizes) {
        this.sizes = sizes;
    }
}
