package com.uncreated.vksimpleapp.model.repository;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model.entity.responses.RequestError;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class WebLoader<T> {
    private static final int AUTH_ERROR = 5;

    @NonNull
    protected final T load(Call<VkResponse<T>> vkResponseCall) throws IOException {
        Response<VkResponse<T>> response = vkResponseCall.execute();
        if (response.isSuccessful()) {
            VkResponse<T> vkResponse = response.body();
            if (vkResponse != null) {
                T t = vkResponse.getResponse();
                if (t != null) {
                    return t;
                }
                RequestError requestError = vkResponse.getError();
                if (requestError != null) {
                    throw new RequestException(requestError);
                }
            }
        }
        throw new RuntimeException("Something wrong with request");
    }

    public boolean handleVkError(EventBus eventBus, RequestError error) {
        switch (error.getErrorCode()) {
            case AUTH_ERROR:
                //eventBus.authPost(Auth.AuthNotValid());TODO:
                break;
            default:
                return false;
        }
        return true;
    }
}
