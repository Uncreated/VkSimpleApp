package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;

import io.reactivex.disposables.CompositeDisposable;

public abstract class Repository<T> {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    Repository(ApiService apiService, EventBus eventBus) {
        applyEventBus(apiService, eventBus);
    }

    protected abstract void applyEventBus(ApiService apiService, EventBus eventBus);

    public void disposeAll() {
        compositeDisposable.dispose();
    }
}
