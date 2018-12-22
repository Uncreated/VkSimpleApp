package com.uncreated.vksimpleapp.model2.entity.vk;

import com.uncreated.vksimpleapp.model2.entity.realm.RealmPhotoInfo;
import com.uncreated.vksimpleapp.model2.entity.realm.RealmPhotoSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhotoInfo {
    private List<PhotoSize> sizes;

    public PhotoInfo(List<PhotoSize> sizes) {
        this.sizes = sizes;
    }

    PhotoInfo(RealmPhotoInfo realmPhotoInfo) {
        this.sizes = new ArrayList<>(realmPhotoInfo.getSizes().size());
        for (RealmPhotoSize realmPhotoSize : realmPhotoInfo.getSizes()) {
            this.sizes.add(new PhotoSize(realmPhotoSize));
        }
    }

    public List<PhotoSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<PhotoSize> sizes) {
        this.sizes = sizes;
    }

    void sort() {
        Collections.sort(sizes, (o1, o2) -> o1.getWidth() - o2.getWidth());
    }

    public String getThumbnailUrl() {
        PhotoSize photoSize = sizes.get(0);
        for (int i = 1; i < sizes.size(); i++) {
            if (sizes.get(i).getWidth() > 150)
                break;
            photoSize = sizes.get(i);
        }
        return photoSize.getUrl();
    }

    public String getOriginalUrl() {
        return sizes.get(sizes.size() - 1).getUrl();
    }
}
