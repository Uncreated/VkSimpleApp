package com.uncreated.vksimpleapp.model2.loader;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.entity.responses.RequestError;
import com.uncreated.vksimpleapp.model2.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model2.entity.vk.Auth;
import com.uncreated.vksimpleapp.model2.repository.auth.AuthRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class WebLoader {
    private static final int ERROR_CODE_AUTH_INVALID = 5;

    private AuthRepository authRepository;

    public WebLoader(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @NonNull
    public <T> T load(@NonNull Call<VkResponse<T>> vkResponseCall) throws IOException {
        Response<VkResponse<T>> response = vkResponseCall.execute();
        if (response.isSuccessful()) {
            VkResponse<T> vkResponse = response.body();
            if (vkResponse != null) {
                T t = vkResponse.getResponse();
                if (t != null) {
                    return t;
                }
                handleError(vkResponse.getError());
            }
        }
        throw new IOException("Something wrong with request");
    }

    private void handleError(RequestError requestError) throws IOException {
        if (requestError != null) {
            switch (requestError.getErrorCode()) {
                case ERROR_CODE_AUTH_INVALID:
                    authRepository.setAuth(Auth.AuthNotValid());
                    break;
            }
            throw new IOException(requestError.getErrorMsg());
        }
    }
}
