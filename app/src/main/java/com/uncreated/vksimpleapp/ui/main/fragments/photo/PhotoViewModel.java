package com.uncreated.vksimpleapp.ui.main.fragments.photo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.events.IndexedBitmap;
import com.uncreated.vksimpleapp.model.entity.events.IndexedUrl;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

public class PhotoViewModel extends ViewModel {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Named("original")
    @Inject
    GalleryPhotoCache galleryPhotoCache;

    private Gallery gallery;

    private LiveData<IndexedBitmap> bitmapIndexLiveData;
    private MutableLiveData<Integer> gallerySizeLiveData = new MutableLiveData<>();

    public PhotoViewModel() {
        App.getApp().getAppComponent().inject(this);

        bitmapIndexLiveData = LiveDataReactiveStreams.fromPublisher(eventBus.getOriginalBitmapPublisher());

        eventBus.originalIndexSubscribe(index -> {
                    if (gallery != null) {
                        String url = gallery.getItems().get(index).getOriginalUrl();
                        eventBus.originalEventPost(new IndexedUrl(index, url));
                    }
                },
                mainThreadScheduler);

        eventBus.gallerySubscribe(gallery -> {
            this.gallery = gallery;
            gallerySizeLiveData.setValue(gallery.getCurrentSize());
        }, mainThreadScheduler);
    }

    public LiveData<IndexedBitmap> getBitmapIndexLiveData() {
        return bitmapIndexLiveData;
    }

    public MutableLiveData<Integer> getGallerySizeLiveData() {
        return gallerySizeLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        galleryPhotoCache.clear();
    }
}
