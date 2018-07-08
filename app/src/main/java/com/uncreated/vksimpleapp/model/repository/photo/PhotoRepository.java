package com.uncreated.vksimpleapp.model.repository.photo;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.request.FutureTarget;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.repository.BitmapIndex;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryCache;

import io.reactivex.schedulers.Schedulers;

public class PhotoRepository implements IPhotoRepository {

    public PhotoRepository(Context context,
                           EventBus eventBus,
                           GalleryCache thumbnailsCache,
                           GalleryCache originalsCache) {
        EventBus.BitmapEvents thumbnailEvents = eventBus.getThumbnailEvents();
        EventBus.BitmapEvents originalEvents = eventBus.getOriginalEvents();

        thumbnailEvents.getUrlSubject()
                .observeOn(Schedulers.io())
                .map(indexUrl -> loadBitmap(context, indexUrl, thumbnailsCache))
                .subscribe(thumbnailEvents.getBitmapSubject());

        originalEvents.getUrlSubject()
                .observeOn(Schedulers.io())
                .map(indexUrl -> loadBitmap(context, indexUrl, originalsCache))
                .subscribe(originalEvents.getBitmapSubject());
    }

    private BitmapIndex loadBitmap(Context context,
                                   IndexUrl indexUrl,
                                   GalleryCache galleryCache) throws Exception {

        FutureTarget<Bitmap> futureBitmap = GlideApp.with(context)
                .asBitmap()
                .load(indexUrl.getUrl())
                .submit();

        Bitmap bitmap = futureBitmap.get();
        galleryCache.putBitmap(indexUrl.getIndex(), bitmap);

        return new BitmapIndex(bitmap, indexUrl.getIndex());
    }
}
