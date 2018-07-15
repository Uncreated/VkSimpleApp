package com.uncreated.vksimpleapp.model.api;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class VkApiInterceptor implements Interceptor {

    private String version;
    private Auth auth;

    public VkApiInterceptor(EventBus eventBus, String version) {
        this.version = version;

        eventBus.authSubscribe(auth -> VkApiInterceptor.this.auth = auth, null);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request;
        if (auth != null && auth.isValid()) {
            Request original = chain.request();

            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("access_token", auth.getAccessToken())
                    .addQueryParameter("v", version)
                    .build();

            request = original.newBuilder()
                    .url(url)
                    .build();
        } else {
            request = chain.request();
        }
        return chain.proceed(request);
    }
}
