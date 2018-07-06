package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.view.photo.PhotoView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<PhotoView> {

    @Named("web")
    @Inject
    Repository repository;

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    private String url;

    public PhotoPresenter(String url) {
        this.url = url;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showPhoto(url);
    }
}
