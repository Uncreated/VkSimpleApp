package com.uncreated.vksimpleapp.model2.entity.vk;

import com.uncreated.vksimpleapp.model2.entity.realm.RealmPhotoSize;

public class PhotoSize {
    private String url;
    private int width;
    private int height;

    PhotoSize(RealmPhotoSize realmPhotoSize) {
        this.url = realmPhotoSize.getUrl();
        this.width = realmPhotoSize.getWidth();
        this.height = realmPhotoSize.getHeight();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
