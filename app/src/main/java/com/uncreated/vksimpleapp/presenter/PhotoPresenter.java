package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.User;
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

    private User user;

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
                    if (user != null) {
                        String url = user.getGallery().getItems().get(index).getOriginalUrl();
                        eventBus.getOriginalEvents()
                                .getUrlSubject()
                                .onNext(new IndexUrl(index, url));
                    }
                }));

        compositeDisposable.add(eventBus.getUserSubject()
                .subscribe(user -> {
                    this.user = user;
                    getViewState().setGallerySize(user.getGallery().getItems().size());
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        galleryPhotoCache.clear();
    }
}
