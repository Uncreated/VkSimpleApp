package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.view.main.MainView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    IAuthRepository authRepository;

    @Named("web")
    @Inject
    Repository webRepository;

    @Named("cache")
    @Inject
    Repository cacheRepository;

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    private User user;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showLoading();

        Auth auth = authRepository.getCurrentAuth();
        if (auth == null || auth.isExpired()) {
            getViewState().goAuth();
        } else {
            onAuthResult();
        }
    }

    public void onAuthResult() {
        Disposable disposable = webRepository.getUser(authRepository.getCurrentAuth().getUserId())
                .observeOn(mainThreadScheduler)
                .subscribe(this::onUserLoaded, this::loadingException);
    }

    private void loadingException(Throwable throwable) {
        throwable.printStackTrace();
        getViewState().hideLoading();
        getViewState().showError(throwable.getMessage());
    }

    private void onUserLoaded(User user) {
        this.user = user;

        getViewState().hideLoading();
        getViewState().setUser(user);
    }

    public void setClicks(Observable<Integer> onClicks) {
        Disposable clickDisposable = onClicks.subscribe(integer -> {
            getViewState().goPhoto(user.getGallery().getItems().get(integer).getOriginalUrl());
        });
    }
}
