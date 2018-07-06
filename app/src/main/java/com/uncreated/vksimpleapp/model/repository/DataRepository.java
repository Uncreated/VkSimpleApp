package com.uncreated.vksimpleapp.model.repository;

import io.reactivex.Observable;

public interface DataRepository<KEY, VALUE> {

    Observable<VALUE> get(KEY key);

    default void set(KEY key, VALUE value) {
        //nothing
    }
}
