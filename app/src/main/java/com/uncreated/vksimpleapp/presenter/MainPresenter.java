package com.uncreated.vksimpleapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.api.ApiClient;
import com.uncreated.vksimpleapp.model.entity.Auth;
import com.uncreated.vksimpleapp.model.entity.User;
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

    @Inject
    ApiClient apiClient;

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
        Disposable disposable = apiClient.getUser()
                .observeOn(mainThreadScheduler)
                .subscribe(this::onUser,
                        throwable -> getViewState().showError(throwable.getMessage()));
    }

    private void onUser(User user) {
        getViewState().hideLoading();
        getViewState().setUserName(user.getFirstName(), user.getLastName());
    }
}
