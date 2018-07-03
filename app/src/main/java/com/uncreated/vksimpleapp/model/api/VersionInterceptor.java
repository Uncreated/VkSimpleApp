package com.uncreated.vksimpleapp.model.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class VersionInterceptor implements Interceptor {

    private String version;

    public VersionInterceptor(String version) {
        this.version = version;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        HttpUrl url = original.url().newBuilder()
                .addQueryParameter("v", version)
                .build();

        Request request = original.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
