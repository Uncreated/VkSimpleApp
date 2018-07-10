package com.uncreated.vksimpleapp.model.entity.responses;

public class VkResponse<T> {

    private T response;
    private RequestError error;

    public T getResponse() {
        return response;
    }

    public RequestError getError() {
        return error;
    }
}
