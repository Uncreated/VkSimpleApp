package com.uncreated.vksimpleapp.model2.repository.photo;

import android.content.Context;
import android.support.annotation.NonNull;

public class PhotoParameters {

    private Context context;
    private String url;

    public PhotoParameters(@NonNull Context context, @NonNull String url) {
        this.context = context;
        this.url = url;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @Override
    @NonNull
    public String toString() {
        return "PhotoParameters{" +
                "context=" + context +
                ", url='" + url + '\'' +
                '}';
    }
}
