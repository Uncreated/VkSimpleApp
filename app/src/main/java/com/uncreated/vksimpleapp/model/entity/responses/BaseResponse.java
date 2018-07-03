package com.uncreated.vksimpleapp.model.entity.responses;

import com.uncreated.vksimpleapp.model.entity.RequestError;

public abstract class BaseResponse {

    protected RequestError requestError;

    public RequestError getRequestError() {
        return requestError;
    }
}
