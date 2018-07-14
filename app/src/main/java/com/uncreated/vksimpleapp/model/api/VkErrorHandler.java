package com.uncreated.vksimpleapp.model.api;

import com.uncreated.vksimpleapp.model.EventBus;

import io.reactivex.disposables.Disposable;

public class VkErrorHandler {
    private static final int AUTH_ERROR = 5;

    public VkErrorHandler(EventBus eventBus) {
        Disposable disposable = eventBus.getVkErrorSubject()
                .subscribe(error -> {
                    if (error.getErrorCode() == AUTH_ERROR) {
                        eventBus.getAuthNotValidSubject()
                                .onNext(false);
                    }
                });
    }


}
