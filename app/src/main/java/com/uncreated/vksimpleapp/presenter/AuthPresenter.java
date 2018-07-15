package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.auth.AuthWebClient;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.view.auth.AuthView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {

    @Inject
    AuthWebClient authWebClient;

    @Inject
    EventBus eventBus;

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        compositeDisposable.add(eventBus.authSubscribe(
                auth -> {
                    if (auth.isValid()) {
                        getViewState().goMain();
                    }
                },
                mainThreadScheduler));

        getViewState().go(authWebClient.getAuthUrl(), authWebClient);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }
}
