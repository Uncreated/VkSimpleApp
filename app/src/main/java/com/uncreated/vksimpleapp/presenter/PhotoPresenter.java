package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryCache;
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
    GalleryCache galleryCache;
    private User user;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        user = eventBus.getUserSubject().getValue();

        compositeDisposable.add(eventBus.getOriginalEvents()
                .getBitmapSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(bitmapIndex -> getViewState().showPhoto(bitmapIndex),
                        Throwable::printStackTrace));

        eventBus.getOriginalEvents()
                .getIndexSubject()
                .subscribeOn(mainThreadScheduler)
                .map(index -> {
                    String url = user.getGallery().getItems().get(index).getOriginalUrl();
                    return new IndexUrl(index, url);
                }).subscribe(eventBus.getOriginalEvents().getUrlSubject());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        galleryCache.clear();
    }
}
