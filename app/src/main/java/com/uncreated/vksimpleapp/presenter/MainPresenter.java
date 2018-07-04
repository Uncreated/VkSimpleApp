package com.uncreated.vksimpleapp.presenter;

import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.entity.Auth;
import com.uncreated.vksimpleapp.model.entity.User;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.view.main.MainView;

import javax.inject.Inject;
import javax.inject.Named;

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
                .subscribe(this::onUser, this::loadingException);
    }

    private void onUser(User user) {
        getViewState().hideLoading();
        getViewState().setUserName(user.getFirstName(), user.getLastName());

        Disposable disposable = webRepository.getPhoto(user.getPhotoUrl())
                .observeOn(mainThreadScheduler)
                .subscribe(this::onAvatar, this::loadingException);
    }

    private void onAvatar(Bitmap bitmap) {
        getViewState().setUserAvatar(bitmap);
    }

    private void loadingException(Throwable throwable) {
        throwable.printStackTrace();
        getViewState().hideLoading();
        getViewState().showError(throwable.getMessage());
    }
}
