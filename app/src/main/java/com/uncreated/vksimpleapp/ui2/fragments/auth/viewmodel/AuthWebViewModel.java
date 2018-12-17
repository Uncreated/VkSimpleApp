package com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;

public class AuthWebViewModel extends ViewModel {
    private static final int CLIENT_ID = 6622858;
    private static final int AUTH_SCOPE = 2 + 4; //friends + photos
    private static final String REDIRECT_HOST = "uncreated.com";
    //TODO:move to model or di

    @Inject
    @Named("version")
    String version;

    @Inject
    IAuthRepository authRepository;

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            url = url.replace('#', '?');
            Uri uri = Uri.parse(url);
            String accessToken = uri.getQueryParameter("access_token");
            String userId = uri.getQueryParameter("user_id");
            String expiresIn = uri.getQueryParameter("expires_in");
            if (REDIRECT_HOST.equals(uri.getHost())
                    && accessToken != null
                    && userId != null
                    && expiresIn != null) {
                Long expiredDate = Auth.calcExpiredDate(Long.parseLong(expiresIn));

                authRepository.setAuth(new Auth(userId, accessToken, expiredDate));
                return true;
            }
            return false;
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
            Uri uri = request.getUrl();
            return shouldOverrideUrlLoading(webView, uri.toString());
        }

        //TODO: handle user pages
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            //int code = error.getErrorCode();
            //String msg = error.getDescription().toString();

            //TODO:handle error
        }


    };

    //TODO: make it single
    private MutableLiveData<String> urlLiveData = new MutableLiveData<>();
    private Disposable authDisposable;

    public AuthWebViewModel() {
        App.getApp().getAppComponent().inject(this);

        authDisposable = authRepository.getAuthObservable()
                .subscribe(auth -> {
                    if (!auth.isValid()) {
                        logout();
                        urlLiveData.setValue(getAuthUrl());
                    }
                });

        urlLiveData.setValue(getAuthUrl());
    }

    public WebViewClient getWebViewClient() {
        return webViewClient;
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

    private String getAuthUrl() {
        return "https://oauth.vk.com/authorize" +
                "?" + "client_id=" + CLIENT_ID +
                "&" + "display=mobile" +
                "&" + "redirect_uri=" + REDIRECT_HOST +
                "&" + "scope=" + AUTH_SCOPE +
                "&" + "response_type=token" +
                "&" + "v=" + version;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        authDisposable.dispose();
    }
}
