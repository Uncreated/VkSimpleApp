package com.uncreated.vksimpleapp.model.entity.realm;

import com.uncreated.vksimpleapp.model.entity.vk.PhotoSize;

import io.realm.RealmObject;

public class RealmPhotoSize extends RealmObject {
    private String url;
    private int width;
    private int height;

    public RealmPhotoSize() {
    }

    RealmPhotoSize(PhotoSize photoSize) {
        this.url = photoSize.getUrl();
        this.width = photoSize.getWidth();
        this.height = photoSize.getHeight();
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
