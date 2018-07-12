package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;
import com.uncreated.vksimpleapp.view.main.MainView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    EventBus eventBus;

    private Gallery gallery;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showLoading();

        compositeDisposable.add(eventBus.getClickThumbnailSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(index -> getViewState().goPhoto(index)));

        eventBus.getThumbnailEvents()
                .getIndexSubject()
                .map(index -> {
                    String url = gallery.getItems().get(index).getThumbnailUrl();
                    return new IndexUrl(index, url);
                }).subscribe(eventBus.getThumbnailEvents().getUrlSubject());

        compositeDisposable.add(eventBus.getThumbnailEvents()
                .getBitmapSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(bitmapIndex -> getViewState().updateThumbnail(bitmapIndex.getIndex()),
                        Throwable::printStackTrace));

        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(user -> getViewState().setUser(user),
                        this::loadingException));

        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(mainThreadScheduler)
                .subscribe(gallery -> {
                    this.gallery = gallery;
                    getViewState().setGallery(gallery.getItems().size());
                    if (gallery.getItems().size() == gallery.getSize()) {
                        getViewState().hideLoading();
                    }
                }));

        compositeDisposable.add(eventBus.getAuthNotValidSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(o -> getViewState().goAuth()));
    }

    private void loadingException(Throwable throwable) {
        throwable.printStackTrace();
        getViewState().hideLoading();
        getViewState().showError(throwable.getMessage());
    }
}
