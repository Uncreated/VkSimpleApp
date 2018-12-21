package com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

public class AuthWebViewClient extends WebViewClient {
    private static final int CLIENT_ID = 6622858;
    private static final int AUTH_SCOPE = 2 + 4; //friends + photos
    private static final String REDIRECT_HOST = "uncreated.com";
    //TODO:move to model or di

    private IAuthRepository authRepository;
    private String version;

    public AuthWebViewClient(IAuthRepository authRepository, String version) {
        this.authRepository = authRepository;
        this.version = version;
    }

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
        return shouldOverrideUrlLoading(webView, request.getUrl().toString());
    }

    String getAuthUrl() {
        return "https://oauth.vk.com/authorize" +
                "?" + "client_id=" + CLIENT_ID +
                "&" + "display=mobile" +
                "&" + "redirect_uri=" + REDIRECT_HOST +
                "&" + "scope=" + AUTH_SCOPE +
                "&" + "response_type=token" +
                "&" + "v=" + version;
    }
}
