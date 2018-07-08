package com.uncreated.vksimpleapp.model.repository;

import android.graphics.Bitmap;

public class BitmapIndex {
    private Bitmap bitmap;
    private Integer index;

    public BitmapIndex(Bitmap bitmap, Integer index) {
        this.bitmap = bitmap;
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
