package com.uncreated.vksimpleapp.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;

public class AutoGridLayoutManager extends GridLayoutManager {
    public AutoGridLayoutManager(Context context, int size) {
        super(context, getSpanCount(context, size));
    }

    private static int getSpanCount(Context context, int size) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / size);
    }
}
