package com.uncreated.vksimpleapp.model2.repository;

import io.reactivex.Observable;

public abstract class Repository<KEY, VALUE> {
    public abstract Observable<VALUE> getObservable(KEY key);
}
