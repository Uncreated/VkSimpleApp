package com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebViewClient;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class AuthViewModel extends ViewModel {

    @Inject
    IAuthRepository authRepository;

    @Inject
    AuthWebViewClient webClient;

    //TODO: make it single
    private MutableLiveData<String> urlLiveData = new MutableLiveData<>();
    private Disposable authDisposable;

    public AuthViewModel() {
        App.getApp().getAppComponent().inject(this);

        authDisposable = authRepository.getAuthObservable()
                .subscribe(auth -> {
                    if (!auth.isValid()) {
                        logout();
                        urlLiveData.setValue(webClient.getAuthUrl());
                    }
                });

        urlLiveData.setValue(webClient.getAuthUrl());
    }

    public WebViewClient getWebViewClient() {
        return webClient;
    }

    public LiveData<String> getUrlLiveData() {
        return urlLiveData;
    }

    public void logout() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeAllCookie();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        authDisposable.dispose();
    }
}
