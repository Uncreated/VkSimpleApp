package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.entity.responses.RequestError;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class WebLoader<T> {

    protected final T load(Call<VkResponse<T>> vkResponseCall) throws IOException {
        Response<VkResponse<T>> response = vkResponseCall.execute();
        if (response.isSuccessful()) {
            VkResponse<T> vkResponse = response.body();
            if (vkResponse != null) {
                T t = vkResponse.getResponse();
                if (t != null) {
                    return t;
                }
                RequestError error = vkResponse.getError();
                throw new RequestException(error);
            }
        }
        throw new RuntimeException();
    }
}
