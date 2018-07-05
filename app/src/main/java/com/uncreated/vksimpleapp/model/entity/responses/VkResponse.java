package com.uncreated.vksimpleapp.model.entity.responses;

public class VkResponse<T> {

    private T response;
    private RequestError requestError;

    public T getResponse() {
        return response;
    }

    public RequestError getRequestError() {
        return requestError;
    }
}
