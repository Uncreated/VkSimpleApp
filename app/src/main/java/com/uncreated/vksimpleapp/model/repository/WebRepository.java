package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestError;

public abstract class WebRepository<T> extends Repository<T> {

    public WebRepository(ApiService apiService, EventBus eventBus) {
        super(apiService, eventBus);
    }

    protected void errorHandle(RequestError requestError, EventBus eventBus) {
        if (requestError != null) {
            eventBus.getVkErrorSubject()
                    .onNext(requestError);
        } else {
            throw new RuntimeException("Unavailable response");
        }
    }
}
