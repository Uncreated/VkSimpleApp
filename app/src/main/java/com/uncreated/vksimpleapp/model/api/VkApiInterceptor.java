package com.uncreated.vksimpleapp.model.api;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class VkApiInterceptor implements Interceptor {

    private IAuthRepository authRepository;
    private String version;

    public VkApiInterceptor(IAuthRepository authRepository, String version) {
        this.authRepository = authRepository;
        this.version = version;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Auth auth = authRepository.getCurrentAuth();

        Request request;
        if (auth != null) {
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
