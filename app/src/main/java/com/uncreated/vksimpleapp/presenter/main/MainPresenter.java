package com.uncreated.vksimpleapp.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.view.main.MainView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    EventBus eventBus;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        disposables.add(eventBus.userSubscribe(
                user -> getViewState().setUser(user),
                mainThreadScheduler));

        disposables.add(eventBus.gallerySubscribe(
                gallery -> getViewState().setGallerySize(gallery.getCurrentSize()),
                mainThreadScheduler));

        disposables.add(eventBus.authSubscribe(
                auth -> {
                    if (!auth.isValid()) {
                        getViewState().goAuth();
                    }
                }, mainThreadScheduler));

        disposables.add(eventBus.themeIdSubscribe(
                themeId -> getViewState().changeTheme(themeId),
                mainThreadScheduler));
    }

    public void onLogout() {
        eventBus.authPost(Auth.AuthLogout());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disposables.dispose();
    }
}
