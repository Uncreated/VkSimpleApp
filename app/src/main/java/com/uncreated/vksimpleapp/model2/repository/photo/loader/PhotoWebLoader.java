package com.uncreated.vksimpleapp.model2.repository.photo.loader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.common.GlideApp;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoLoader;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoParameters;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PhotoWebLoader extends PhotoLoader {
    @Override
    @NonNull
    public Bitmap load(@NonNull PhotoParameters photoParameters) throws IOException {
        try {
            return GlideApp.with(photoParameters.getContext())
                    .asBitmap()
                    .load(photoParameters.getUrl())
                    .submit()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            throw new IOException(e.getMessage());
        }
    }
}
