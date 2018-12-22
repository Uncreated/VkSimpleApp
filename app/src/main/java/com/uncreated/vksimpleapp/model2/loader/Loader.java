package com.uncreated.vksimpleapp.model2.loader;

import android.support.annotation.NonNull;

import java.io.IOException;

public abstract class Loader<KEY, VALUE> {
    @NonNull
    public abstract VALUE load(@NonNull KEY key) throws IOException;

    public void save(@NonNull KEY key, @NonNull VALUE value) throws IOException {
        //nothing
    }
}
