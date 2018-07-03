package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.auth.AuthWebClient;
import com.uncreated.vksimpleapp.model.entity.Auth;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.view.auth.AuthView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {

    @Inject
    AuthWebClient authWebClient;

    @Inject
    IAuthRepository authRepository;

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Override
    public void attachView(AuthView view) {
        super.attachView(view);

        doAuth();
    }

    private void doAuth() {
        Disposable disposable = authWebClient.subscribe()
                .subscribeOn(mainThreadScheduler)
                .subscribe(this::onAuth, throwable -> {
                    getViewState().showError(throwable.getMessage());
                    doAuth();
                });
        getViewState().go(authWebClient.getAuthUrl(), authWebClient);
    }

    private void onAuth(Auth auth) {
        authRepository.setCurrentAuth(auth);
        getViewState().goBackView();
    }
}
