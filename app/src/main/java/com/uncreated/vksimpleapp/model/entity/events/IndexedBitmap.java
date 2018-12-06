package com.uncreated.vksimpleapp.model.entity.events;

import android.graphics.Bitmap;

public class IndexedBitmap {
    private Bitmap bitmap;
    private Integer index;

    public IndexedBitmap(Bitmap bitmap, Integer index) {
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
