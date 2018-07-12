package com.uncreated.vksimpleapp.model.entity.realm;

import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoSize;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmPhotoInfo extends RealmObject {
    private RealmList<RealmPhotoSize> sizes;

    public RealmPhotoInfo() {
    }

    RealmPhotoInfo(PhotoInfo photoInfo) {
        this.sizes = new RealmList<>();
        for (PhotoSize photoSize : photoInfo.getSizes()) {
            this.sizes.add(new RealmPhotoSize(photoSize));
        }
    }

    public RealmList<RealmPhotoSize> getSizes() {
        return sizes;
    }

    public void setSizes(RealmList<RealmPhotoSize> sizes) {
        this.sizes = sizes;
    }
}
