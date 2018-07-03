package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.auth.AuthWebClient;
import com.uncreated.vksimpleapp.view.auth.AuthView;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {

    @Inject
    AuthWebClient authWebClient;
    private Scheduler mainThreadScheduler;

    public AuthPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void permissionGranted() {
        Disposable disposable = authWebClient.subscribe()
                .subscribeOn(mainThreadScheduler)
                .subscribe(auth -> {

                }, throwable -> {
                    getViewState().showError(throwable.getMessage());
                    permissionGranted();
                });
        getViewState().go(authWebClient.getAuthUrl(), authWebClient);
    }
}
