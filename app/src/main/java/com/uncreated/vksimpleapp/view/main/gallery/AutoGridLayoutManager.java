package com.uncreated.vksimpleapp.view.main.gallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;

public class AutoGridLayoutManager extends GridLayoutManager {
    public AutoGridLayoutManager(Context context, int size, int interval) {
        super(context, getSpanCount(context, size, interval));
    }

    private static int getSpanCount(Context context, int size, int interval) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) ((dpWidth - interval) / (size + interval));
    }
}
