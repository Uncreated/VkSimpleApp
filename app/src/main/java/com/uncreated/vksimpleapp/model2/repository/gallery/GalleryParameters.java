package com.uncreated.vksimpleapp.model2.repository.gallery;

public class GalleryParameters {
    private String userId;
    private int offset;

    public GalleryParameters(String userId, int offset) {
        this.userId = userId;
        this.offset = offset;
    }

    public String getUserId() {
        return userId;
    }

    public int getOffset() {
        return offset;
    }
}
