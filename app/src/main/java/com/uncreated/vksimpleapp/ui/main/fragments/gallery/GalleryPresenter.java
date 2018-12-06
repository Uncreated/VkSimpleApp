package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.entity.events.IndexedUrl;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class GalleryPresenter extends MvpPresenter<GalleryView> {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Gallery gallery;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showLoading();

        compositeDisposable.add(eventBus.clickThumbnailSubscribe(
                index -> getViewState().goPhoto(index),
                mainThreadScheduler));

        compositeDisposable.add(eventBus.thumbnailIndexSubscribe(
                index -> {
                    String url = gallery.getItems().get(index).getThumbnailUrl();
                    eventBus.thumbnailEventsPost(new IndexedUrl(index, url));
                }, mainThreadScheduler));


        compositeDisposable.add(eventBus.thumbnailBitmapIndexSubscribe(
                bitmapIndex -> getViewState().updateThumbnail(bitmapIndex.getIndex()),
                mainThreadScheduler));

        compositeDisposable.add(eventBus.gallerySubscribe(
                gallery -> {
                    this.gallery = gallery;
                    int size = gallery.getCurrentSize();
                    getViewState().setGallery(size);
                    if (size == gallery.getCount()) {
                        getViewState().hideLoading();
                    }
                }, mainThreadScheduler));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }
}
