package com.uncreated.vksimpleapp.ui.main.fragments.photo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<PhotoView> {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    EventBus eventBus;
    @Named("original")
    @Inject
    GalleryPhotoCache galleryPhotoCache;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Gallery gallery;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        compositeDisposable.add(eventBus.originalBitmapIndexSubscribe(
                bitmapIndex -> getViewState().updatePhoto(bitmapIndex),
                mainThreadScheduler));

        compositeDisposable.add(eventBus.originalIndexSubscribe(
                index -> {
                    if (gallery != null) {
                        String url = gallery.getItems().get(index).getOriginalUrl();
                        eventBus.originalEventPost(new IndexUrl(index, url));
                    }
                },
                mainThreadScheduler));

        compositeDisposable.add(eventBus.gallerySubscribe(
                gallery -> {
                    this.gallery = gallery;
                    getViewState().setGallerySize(gallery.getCurrentSize());
                }, mainThreadScheduler));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        galleryPhotoCache.clear();
    }
}
