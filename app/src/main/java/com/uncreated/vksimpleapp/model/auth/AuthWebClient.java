package com.uncreated.vksimpleapp.model.auth;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uncreated.vksimpleapp.model.entity.Auth;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class AuthWebClient extends WebViewClient {

    private static final int CLIENT_ID = 6622858;
    private static final int AUTH_SCOPE = 2 + 4; //friends + photos
    private static final String REDIRECT_HOST = "uncreated.com";
    private static final String VK_SDK_VERSION = "5.80";

    private Subject<Auth> subject;

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        url = url.replace('#', '?');
        Uri uri = Uri.parse(url);
        String accessToken = uri.getQueryParameter("access_token");
        String userId = uri.getQueryParameter("user_id");
        if (uri.getHost().equals(REDIRECT_HOST) && accessToken != null && userId != null) {
            subject.onNext(new Auth(userId, accessToken));
            subject.onComplete();
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

    public String getAuthUrl() {
        return "https://oauth.vk.com/authorize" +
                "?" + "client_id=" + CLIENT_ID +
                "&" + "display=mobile" +
                "&" + "redirect_uri=" + REDIRECT_HOST +
                "&" + "scope=" + AUTH_SCOPE +
                "&" + "response_type=token" +
                "&" + "v=" + VK_SDK_VERSION;
    }

    public Subject<Auth> subscribe() {
        subject = PublishSubject.create();
        return subject;
    }
}
