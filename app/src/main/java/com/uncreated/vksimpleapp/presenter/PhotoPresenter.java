package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;
import com.uncreated.vksimpleapp.view.photo.PhotoView;

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

        compositeDisposable.add(eventBus.getOriginalEvents()
                .getBitmapSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(bitmapIndex -> getViewState().updatePhoto(bitmapIndex),
                        throwable -> getViewState().showError(throwable.getMessage())));

        compositeDisposable.add(eventBus.getOriginalEvents()
                .getIndexSubject()
                .subscribeOn(mainThreadScheduler)
                .subscribe(index -> {
                    if (gallery != null) {
                        String url = gallery.getItems().get(index).getOriginalUrl();
                        eventBus.getOriginalEvents()
                                .getUrlSubject()
                                .onNext(new IndexUrl(index, url));
                    }
                }));

        compositeDisposable.add(eventBus.getGallerySubject()
                .subscribe(gallery -> {
                    this.gallery = gallery;
                    getViewState().setGallerySize(gallery.getItems().size());
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        galleryPhotoCache.clear();
    }
}
