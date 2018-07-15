package com.uncreated.vksimpleapp.model.entity.responses;

public class RequestException extends RuntimeException {
    private RequestError requestError;

    public RequestException(RequestError requestError) {
        this.requestError = requestError;
    }

    public RequestError getRequestError() {
        return requestError;
    }
}
