package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;

public class AutoGridLayoutManager extends GridLayoutManager {

    private int itemSize;

    AutoGridLayoutManager(Context context, float minSize, float margin) {
        super(context, getSpanCount(context, minSize, margin));

        this.itemSize = getItemSize(context, getSpanCount(), margin);
    }

    private static int getSpanCount(Context context, float minSize, float margin) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float totalWidth = margin * 2 + minSize;
        return (int) (displayMetrics.widthPixels / totalWidth);
    }

    private static int getItemSize(Context context, int columns, float margin) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float totalMargin = margin * columns * 2;
        return (int) ((displayMetrics.widthPixels - totalMargin) / columns);
    }

    public int getItemSize() {
        return itemSize;
    }


}
