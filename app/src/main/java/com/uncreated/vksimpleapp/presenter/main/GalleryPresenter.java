package com.uncreated.vksimpleapp.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.view.main.gallery.GalleryView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class GalleryPresenter extends MvpPresenter<GalleryView> {

    @Inject
    EventBus eventBus;

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Gallery gallery;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showLoading();

        compositeDisposable.add(eventBus.getClickThumbnailSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(index -> getViewState().goPhoto(index)));

        eventBus.getThumbnailEvents()
                .getIndexSubject()
                .observeOn(mainThreadScheduler)
                .map(index -> {
                    String url = gallery.getItems().get(index).getThumbnailUrl();
                    return new IndexUrl(index, url);
                }).subscribe(eventBus.getThumbnailEvents().getUrlSubject());

        compositeDisposable.add(eventBus.getThumbnailEvents()
                .getBitmapSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(bitmapIndex -> getViewState().updateThumbnail(bitmapIndex.getIndex()),
                        Throwable::printStackTrace));

        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(mainThreadScheduler)
                .subscribe(gallery -> {
                    this.gallery = gallery;
                    getViewState().setGallery(gallery.getItems().size());
                    if (gallery.getItems().size() == gallery.getCount()) {
                        getViewState().hideLoading();
                    }
                }));
    }
}
