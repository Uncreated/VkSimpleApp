package com.uncreated.vksimpleapp.model.auth;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;

import io.reactivex.disposables.Disposable;

public class AuthWebClient extends WebViewClient {

    private static final int CLIENT_ID = 6622858;
    private static final int AUTH_SCOPE = 2 + 4; //friends + photos
    private static final String REDIRECT_HOST = "uncreated.com";

    private EventBus eventBus;

    private String version;

    public AuthWebClient(EventBus eventBus, String version) {
        this.eventBus = eventBus;
        this.version = version;

        Disposable disposable = eventBus.getAuthNotValidSubject()
                .subscribe(logout -> {
                    if (logout) {
                        CookieManager cookieManager = CookieManager.getInstance();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cookieManager.removeAllCookies(null);
                            cookieManager.flush();
                        } else
                            cookieManager.removeAllCookie();
                    }
                });
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        url = url.replace('#', '?');
        Uri uri = Uri.parse(url);
        String accessToken = uri.getQueryParameter("access_token");
        String userId = uri.getQueryParameter("user_id");
        String expiresIn = uri.getQueryParameter("expires_in");
        if (uri.getHost().equals(REDIRECT_HOST)
                && accessToken != null
                && userId != null
                && expiresIn != null) {
            Long expiredDate = Auth.calcExpiredDate(Long.parseLong(expiresIn));

            Auth auth = new Auth(userId, accessToken, expiredDate);
            eventBus.getAuthSubject()
                    .onNext(auth);
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
                "&" + "v=" + version;
    }
}
