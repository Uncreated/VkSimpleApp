package com.uncreated.vksimpleapp.model2.entity.responses;

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
