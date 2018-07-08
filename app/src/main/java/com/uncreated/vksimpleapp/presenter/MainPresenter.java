package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.User;
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

    private User user;

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
                    String url = user.getGallery().getItems().get(index).getThumbnailUrl();
                    return new IndexUrl(index, url);
                }).subscribe(eventBus.getThumbnailEvents().getUrlSubject());

        compositeDisposable.add(eventBus.getThumbnailEvents()
                .getBitmapSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(bitmapIndex -> getViewState().updateThumbnail(bitmapIndex.getIndex()),
                        Throwable::printStackTrace));

        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {
                    this.user = user;

                    getViewState().hideLoading();
                    getViewState().setUser(user);
                }, this::loadingException));

        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(mainThreadScheduler)
                .subscribe(gallery ->
                        getViewState().setGallery(user.getGallery().getItems().size())));

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
